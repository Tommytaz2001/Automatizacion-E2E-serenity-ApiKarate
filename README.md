# QA Automation — NTT Data Technical Test

Proyecto multi-módulo Gradle con dos ejercicios de automatización:

| Módulo | Framework | Target |
|--------|-----------|--------|
| `e2e-tests` | SerenityBDD 3.9.8 + Screenplay + Cucumber 7.x | https://www.saucedemo.com |
| `api-tests` | Karate 1.4.0 + JUnit 5 | https://petstore.swagger.io/v2 |

---

## Instalación del Entorno

Sigue estos pasos en orden antes de ejecutar los tests por primera vez.

---

### Paso 1 — Instalar JDK

Verifica si ya tienes Java instalado:
```cmd
java -version
```

Si no tienes Java o la versión es menor a 11, instala JDK 17 (recomendado):

**Opción A — WinGet (Windows 10/11, recomendado):**
```cmd
winget install EclipseAdoptium.Temurin.17.JDK
```

**Opción B — Chocolatey:**
```cmd
choco install temurin17
```

**Opción C — Descarga manual:**
Descargar desde https://adoptium.net → seleccionar **Temurin 17** → instalador Windows.

> **Nota:** Java 8 no es compatible. Java 11, 17 o 24 funcionan correctamente.
> Si tienes Java 21 o 24, usa siempre `mvnw.cmd` (no `gradlew.bat`).

Después de instalar, verifica:
```cmd
java -version
```
Debe mostrar `openjdk version "17"` o superior.

---

### Paso 2 — Instalar Google Chrome

Solo para los tests E2E. Verifica si ya lo tienes:
```cmd
"C:\Program Files\Google\Chrome\Application\chrome.exe" --version
```

Si no está instalado:
```cmd
winget install Google.Chrome
```

O descargarlo desde https://www.google.com/chrome/

> El ChromeDriver (148.x) ya está incluido en la carpeta `drivers/` del proyecto.
> No necesitas descargarlo manualmente.

---

### Paso 3 — Clonar o descomprimir el proyecto

Si tienes Git:
```cmd
git clone <URL-del-repositorio>
cd qa-ntt-data
```

Si tienes el ZIP del proyecto, descomprímelo y abre una terminal en la carpeta `qa-ntt-data/`.

---

### Paso 4 — Verificar que todo está listo

```cmd
java -version
```
✅ Debe mostrar Java 11 o superior.

```cmd
"C:\Program Files\Google\Chrome\Application\chrome.exe" --version
```
✅ Debe mostrar la versión de Chrome.

```cmd
.\mvnw.cmd --version
```
✅ Debe descargar Maven automáticamente y mostrar `Apache Maven 3.9.1`.

> Maven y todas las dependencias del proyecto (SerenityBDD, Karate, etc.) se
> descargan automáticamente en la primera ejecución de `mvnw.cmd`. No necesitas
> instalar nada más.

---

## Prerequisitos Resumen

| Herramienta | Versión | Instalación manual |
|-------------|---------|-------------------|
| JDK | 11, 17 o 24 | Sí — ver Paso 1 |
| Google Chrome | Cualquier versión reciente | Sí — ver Paso 2 |
| Maven | 3.9.1 | No — `mvnw.cmd` lo descarga solo |
| ChromeDriver | 148.x | No — incluido en `drivers/` |
| SerenityBDD, Karate y demás | — | No — Maven los descarga solos |

> **Acceso a internet requerido** en la primera ejecución para que Maven descargue
> las dependencias (~200 MB). Las siguientes ejecuciones son offline.

---

## Estructura del Proyecto

```
qa-ntt-data/
├── e2e-tests/                        # Ejercicio 1: SerenityBDD + Screenplay
│   ├── serenity.properties           # Configuración de browser y reportes
│   └── src/test/
│       ├── java/com/nttdata/qa/
│       │   ├── runners/              # CucumberTestRunner
│       │   ├── stepdefinitions/      # Glue code de Cucumber
│       │   ├── tasks/                # Screenplay Tasks (Login, AddProductsToCart…)
│       │   ├── questions/            # Screenplay Questions (ConfirmationMessage)
│       │   ├── ui/                   # Target locators (selectores CSS/XPath)
│       │   └── utils/                # CsvDataReader
│       └── resources/
│           ├── features/purchase/    # complete_purchase.feature
│           └── testdata/users.csv    # Datos de prueba CSV
│
└── api-tests/                        # Ejercicio 2: Karate Framework
    └── src/test/
        ├── java/com/nttdata/qa/runners/  # KarateTestRunner
        └── resources/
            ├── karate-config.js          # URL base y headers
            ├── com/nttdata/qa/petstore/  # petstore.feature
            └── testdata/pet.json         # Datos de prueba JSON
```

---

## Ejecutar las Pruebas

El proyecto incluye **dos opciones de build**: Maven 3.9.1 (`mvnw.cmd`) y Gradle 7.6.1 (`gradlew.bat`).
Usar Maven si tu JDK es 20+. Usar Gradle si tu JDK es 11 o 17.

---

### Con Maven (compatible con JDK 11, 17, 24)

**Solo pruebas API:**
```cmd
.\mvnw.cmd test -pl api-tests
```

**Solo pruebas E2E + reporte Serenity:**
```cmd
.\mvnw.cmd test -pl e2e-tests
cd e2e-tests
..\mvnw.cmd serenity:aggregate
cd ..
```

**Todas las pruebas:**
```cmd
.\mvnw.cmd test
```

---

### Con Gradle (compatible con JDK 11, 17 — requiere JDK ≤ 19)

**Todas las pruebas:**
```cmd
gradlew.bat test
```

**Solo pruebas E2E (con reporte Serenity):**
```cmd
gradlew.bat :e2e-tests:test aggregate
```

**Solo pruebas API:**
```cmd
gradlew.bat :api-tests:test
```

---

### E2E con navegador visible (modo debug)

1. Abrir `e2e-tests/serenity.properties`
2. Cambiar la línea:
   ```properties
   chrome.switches=--no-sandbox,--disable-dev-shm-usage,--disable-gpu,--window-size=1920,1080
   ```
   por:
   ```properties
   chrome.switches=--no-sandbox,--disable-dev-shm-usage,--window-size=1920,1080
   ```
3. Ejecutar:
   ```cmd
   mvnw.cmd test -pl e2e-tests
   ```

---

## Ubicación de los Reportes

### E2E — Serenity HTML Report (Maven y Gradle)

```
e2e-tests\target\site\serenity\index.html
```

Abrir en cualquier navegador. Incluye:
- Estado de cada escenario (pass/fail)
- Capturas de pantalla por paso
- Trazabilidad de requerimientos

### E2E — Cucumber HTML Report

```
e2e-tests\target\cucumber-reports\cucumber-html-report\
```

### API — Karate HTML Report

**Maven:**
```
api-tests\target\karate-reports\karate-summary.html
```
**Gradle:**
```
api-tests\build\karate-reports\karate-summary.html
```

Abrir en cualquier navegador. Incluye:
- Resultado de cada escenario
- Detalle completo de request/response HTTP

---

## Datos de Prueba

### E2E: `e2e-tests/src/test/resources/testdata/users.csv`

```csv
username,password,firstName,lastName,zipCode,productCount
standard_user,secret_sauce,John,Doe,12345,2
standard_user,secret_sauce,Jane,Smith,67890,2
```

Los valores de la tabla `Examples` en el `Scenario Outline` se alimentan de este archivo.

### API: `api-tests/src/test/resources/testdata/pet.json`

```json
{
  "newPet": { "name": "Buddy", "status": "available", ... },
  "updatedName": "Buddy Updated",
  "updatedStatus": "sold"
}
```

Karate lo carga con `read('classpath:testdata/pet.json')`.

---

## Versiones de Tecnologías

| Tecnología | Versión |
|-----------|---------|
| Gradle | 7.6.1 |
| JDK | 11 (mínimo) |
| SerenityBDD | 3.9.8 |
| Cucumber | 7.14.0 |
| Karate | 1.4.0 |
| JUnit 4 | 4.13.2 |
| JUnit 5 | 5.10.1 |
| WebDriverManager | 5.6.3 |
| OpenCSV | 5.8 |

---

## Problemas Encontrados y Soluciones

### 1. Gradle 7.6.1 incompatible con JDK 24
**Problema:** Gradle 7.6.1 solo soporta hasta JDK 19. Al tener JDK 24 instalado, el build fallaba con `Unsupported class file major version 68`.
**Solución:** Se agregó soporte para Maven 3.9.1 (`mvnw.cmd`) como alternativa. Maven es compatible con JDK 24.

---

### 2. Karate + GraalVM incompatible con JDK 24
**Problema:** Karate 1.4.0 incluye GraalVM 22.3.0 como motor JavaScript. GraalVM 22.x llama a `sun.misc.Unsafe.ensureClassInitialized`, método que fue eliminado en JDK 24. Los 4 tests de API fallaban al iniciar.
**Solución:** Se actualizó GraalVM a la versión 24.2.2 (`org.graalvm.sdk:graal-sdk` + `org.graalvm.polyglot:js-community`) en el `pom.xml` del módulo API.

---

### 3. Estado compartido entre escenarios Karate
**Problema:** El ID del pet creado en el escenario POST no estaba disponible en los escenarios GET, PUT y findByStatus. `karate.set()` solo persiste dentro del mismo escenario.
**Solución:** Se creó un `setup.feature` separado y se usa `callonce` en el `Background` para ejecutar el POST una sola vez y compartir el ID entre todos los escenarios del feature.

---

### 4. Popup "Cambia la contraseña" bloqueaba los tests E2E
**Problema:** Chrome 148 detecta que `secret_sauce` (credencial de SauceDemo) aparece en bases de datos de contraseñas comprometidas y muestra un popup que bloqueaba la navegación al formulario de checkout.
**Solución:** Se configuraron Chrome preferences en Java (`credentials_enable_service: false`, `profile.password_manager_enabled: false`) al crear el driver manualmente, deshabilitando el gestor de contraseñas de Chrome.

---

### 5. ChromeDriver no compatible con Chrome 148
**Problema:** Selenium Manager (incluido en Selenium 4.x) no pudo descargar automáticamente el ChromeDriver para Chrome 148 (versión muy reciente).
**Solución:** Se descargó ChromeDriver 148 manualmente desde [Chrome for Testing](https://googlechromelabs.github.io/chrome-for-testing/) y se referencia en `serenity.properties` con `webdriver.chrome.driver`.

---

### 6. Reporte Serenity no generaba archivos de resultado
**Problema:** Al crear el WebDriver manualmente (sin `@Managed`), Serenity no inicializaba su sistema de reportes y no escribía los archivos JSON de resultados.
**Solución:** Se movió `serenity.properties` a `src/test/resources/` para que esté disponible en el classpath, lo que permite que Serenity inicialice correctamente su reporter.

---

## Ubicación de Reportes

| Reporte | Ruta | Descripción |
|---------|------|-------------|
| **Serenity E2E** | `e2e-tests\target\site\serenity\index.html` | Reporte completo con capturas de pantalla por paso |
| **Cucumber E2E** | `e2e-tests\target\cucumber-reports\cucumber-html-report\` | Reporte Cucumber estándar |
| **Karate API** | `api-tests\target\karate-reports\karate-summary.html` | Reporte con detalle de request/response HTTP |

> Abrir cualquiera de estos archivos directamente en el navegador (doble clic o arrastrar).

---

## Solución de Problemas Rápida

| Síntoma | Acción |
|---------|--------|
| `gradlew.bat` falla con JDK 21+ | Usar `.\mvnw.cmd` en su lugar |
| `PetStore API: 504 / timeout` | La API pública puede ser lenta; volver a ejecutar |
| `No scenarios found` | Verificar que el `glue` en `CucumberTestRunner` sea `com.nttdata.qa.stepdefinitions` |
