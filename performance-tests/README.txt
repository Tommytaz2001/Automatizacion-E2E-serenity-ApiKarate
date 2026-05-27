QA Automation - NTT Data - Ejercicio Performance
================================================

Objetivo
--------
Ejecutar una prueba de carga sobre el servicio de login usando k6, con datos de entrada parametrizados desde CSV y validaciones de rendimiento:

- Alcanzar minimo 20 TPS.
- Mantener el tiempo de respuesta p95 por debajo de 1.5 segundos.
- Mantener la tasa de error por debajo del 3%.

Estructura
----------
performance-tests/
  data/users.csv
  scripts/login.load.test.js
  reports/textSummary.txt
  reports/k6-summary.txt
  reports/summary.json
  docs/Captura de pantalla 2026-05-27 184511.png
  docs/InformeResultados.docx
  README.txt
  conclusiones.txt

Versiones utilizadas
--------------------
- k6: 0.49.0 o superior
- Sistema operativo: Windows 10/11
- API objetivo: https://fakestoreapi.com
- Endpoint: POST /auth/login

Instalacion de k6
-----------------
Antes de ejecutar, validar si k6 ya esta instalado:

k6 version

Si el comando responde con una version, se puede ejecutar la prueba directamente.
Si aparece el error "El termino 'k6' no se reconoce", significa que k6 no esta instalado o no esta agregado al PATH del sistema.

Opcion A - WinGet, si esta disponible:

winget install k6.k6

Opcion B - Chocolatey, si esta disponible:

choco install k6

Opcion C - k6 portable para Windows, si no tienes WinGet ni Chocolatey:

Desde la carpeta performance-tests ejecutar:

New-Item -ItemType Directory -Force -Path tools

Invoke-WebRequest `
  -Uri "https://github.com/grafana/k6/releases/download/v0.49.0/k6-v0.49.0-windows-amd64.zip" `
  -OutFile "tools\k6-v0.49.0-windows-amd64.zip"

Expand-Archive `
  -LiteralPath "tools\k6-v0.49.0-windows-amd64.zip" `
  -DestinationPath "tools" `
  -Force

Verificar k6 portable:

.\tools\k6-v0.49.0-windows-amd64\k6.exe version

Datos de prueba
---------------
Los usuarios estan en:

performance-tests/data/users.csv

El archivo contiene las columnas user y passwd solicitadas en el ejercicio.

Ejecucion
---------
Abrir una terminal en la carpeta:

qa-ntt-data/performance-tests

Ejecutar la prueba con la configuracion base de 21 TPS durante 5 minutos:

k6 run scripts/login.load.test.js

Si se usa k6 portable:

.\tools\k6-v0.49.0-windows-amd64\k6.exe run scripts\login.load.test.js

Ejecutar cambiando la tasa o duracion:

k6 run -e RATE=25 -e DURATION=10m scripts/login.load.test.js

Con k6 portable:

.\tools\k6-v0.49.0-windows-amd64\k6.exe run -e RATE=25 -e DURATION=10m scripts\login.load.test.js

Ejecutar contra otra URL base:

k6 run -e BASE_URL=https://fakestoreapi.com scripts/login.load.test.js

Reportes generados
------------------
Al finalizar la ejecucion se generan:

- reports/summary.json
- reports/k6-summary.txt

El archivo reports/textSummary.txt corresponde al resumen suministrado para el ejercicio de analisis de resultados.

Criterios de aceptacion
-----------------------
La prueba se considera aprobada cuando:

- http_req_failed es menor a 3%.
- http_req_duration p95 es menor a 1500 ms.
- checks es mayor a 97%.
- La tasa de peticiones por segundo es igual o superior a 20 TPS.
