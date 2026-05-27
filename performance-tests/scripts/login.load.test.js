import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

const baseUrl = __ENV.BASE_URL || 'https://fakestoreapi.com';
const rate = Number(__ENV.RATE || 21);
const duration = __ENV.DURATION || '5m';

const users = new SharedArray('users', function () {
  return open('../data/users.csv')
    .trim()
    .split('\n')
    .slice(1)
    .map((row) => {
      const [username, password] = row.split(',');
      return { username, password };
    });
});

export const options = {
  scenarios: {
    login_load: {
      executor: 'constant-arrival-rate',
      rate,
      timeUnit: '1s',
      duration,
      preAllocatedVUs: 30,
      maxVUs: 100
    }
  },
  thresholds: {
    http_req_failed: ['rate<0.03'],
    http_req_duration: ['p(95)<1500'],
    checks: ['rate>0.97']
  }
};

export default function () {
  const user = users[__ITER % users.length];
  const payload = JSON.stringify({
    username: user.username,
    password: user.password
  });

  const response = http.post(`${baseUrl}/auth/login`, payload, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json'
    },
    tags: {
      name: 'POST /auth/login'
    }
  });

  check(response, {
    'login status is successful': (res) => [200, 201].includes(res.status),
    'login returns token': (res) => Boolean(res.json().token),
    'login response time is under 1500ms': (res) => res.timings.duration <= 1500
  });
}

export function handleSummary(data) {
  return {
    'reports/summary.json': JSON.stringify(data, null, 2),
    'reports/k6-summary.txt': buildTextSummary(data),
    stdout: buildTextSummary(data)
  };
}

function buildTextSummary(data) {
  const metrics = data.metrics;
  const duration = metrics.http_req_duration.values;
  const failed = metrics.http_req_failed.values;
  const reqs = metrics.http_reqs.values;
  const checks = metrics.checks.values;

  return [
    'Resultado prueba de carga login',
    '',
    `http_reqs...............: ${reqs.count} ${reqs.rate.toFixed(2)}/s`,
    `http_req_failed.........: ${(failed.rate * 100).toFixed(2)}%`,
    `checks..................: ${(checks.rate * 100).toFixed(2)}%`,
    `http_req_duration avg...: ${duration.avg.toFixed(2)}ms`,
    `http_req_duration med...: ${duration.med.toFixed(2)}ms`,
    `http_req_duration p90...: ${duration['p(90)'].toFixed(2)}ms`,
    `http_req_duration p95...: ${duration['p(95)'].toFixed(2)}ms`,
    `http_req_duration max...: ${duration.max.toFixed(2)}ms`,
    '',
    'Validaciones esperadas',
    `TPS minimo..............: ${rate}`,
    'Tiempo respuesta p95....: < 1500ms',
    'Tasa error..............: < 3%',
    'Checks..................: > 97%',
    ''
  ].join('\n');
}
