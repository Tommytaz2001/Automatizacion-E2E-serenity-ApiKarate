function fn() {
  var config = {
    baseUrl: 'https://petstore.swagger.io/v2',
    defaultHeaders: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  };
  karate.configure('url', config.baseUrl);
  karate.configure('connectTimeout', 10000);
  karate.configure('readTimeout', 15000);
  return config;
}
