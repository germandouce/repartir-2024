# Repartir

## Ambiente

 * JDK 17
 * Docker
 * Chrome

## Ejecutar pruebas

Para ejecutar todas las pruebas

```
./gradlew check --info
```

## Para levantar en el ambiente local

```
./gradlew bootRun
```
## Acceso web

http://localhost:8080/

## Open API

http://localhost:8080/swagger-ui.html

## Solución

Se implementó la regla de negocio sugerida:

**El total de un grupo no debe ser negativo**

La implementación calcula el nuevo total antes de actualizar el grupo y rechaza el gasto si el resultado es negativo.

## Pruebas automatizadas

Al ejecutar las pruebas con el comando:

```bash
./gradlew check --info
```

Todas las pruebas pasan, excepto una prueba de `uiTest`, que ya fallaba al inicio del trabajo.

Por este motivo, para verificar la solución se utilizaron las pruebas de tipo `fastAcceptanceTest`, agregando tres escenarios para comprobar el comportamiento de la regla de negocio.

Para ejecutarlas, se utiliza el siguiente comando:

```bash
./gradlew fastAcceptanceTest --rerun-tasks
```

Los tres escenarios pasan correctamente.

## Pruebas manuales

Para probar la solución manualmente, se puede levantar el entorno local y utilizar Swagger para interactuar con la API.

A continuación, se describen los tres escenarios probados.

### Escenario 1: Se puede registrar un gasto que no deja el total en negativo

**Dado** Dado que un grupo tiene un total de 100 pesos

*Crear el grupo*

`POST /api/grupos`

```json
{
  "nombre": "asado",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 0
}
```

*Registrar un gasto de 100 pesos*

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": 100
}
```

**Cuando** se registra un gasto de -30 pesos

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": -30
}
```

**Entonces** el total del grupo es de 70 pesos

*Consultar el grupo*

`GET /api/grupos/{id}`

Código de respuesta esperado: `200 OK`.

```json
{
  "id": 46,
  "nombre": "asado",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 70
}
```

### Escenario 2: Se rechaza un gasto que dejaría el total en negativo

**Dado** que un grupo tiene un total de 100 pesos

*Crear el grupo*

`POST /api/grupos`

```json
{
  "nombre": "noche de pizza",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 0
}
```

*Registrar un gasto de 100 pesos*

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": 100
}
```

**Cuando** se intenta registrar un gasto de -150 pesos:

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": -150
}
```

**Entonces** el gasto no se registra

`400 Bad Request`


Y el total del grupo es de 100 pesos

*Consultar el grupo*

`GET /api/grupos/{id}`

Código de respuesta esperado: `200 OK`.

```json
{
  "id": 47,
  "nombre": "noche de pizza",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 100
}
```

### Escenario 3: Se puede registrar un gasto que deja el total en cero

**Dado** que un grupo tiene un total de 100 pesos


*Crear el grupo*

`POST /api/grupos`

```json
{
  "nombre": "noche de empanadas",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 0
}
```

*Registrar un gasto de 100 pesos*

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": 100
}
```

**Cuando** se registra un gasto de -100 pesos:

`POST /api/grupos/{id}/gastos`

```json
{
  "monto": -100
}
```

**Entonces** el total del grupo es de 0 pesos.

*Consultar el grupo*

`GET /api/grupos/{id}`

Código de respuesta esperado: `200 OK`.

```json
{
  "id": 48,
  "nombre": "noche de empanadas",
  "miembros": [
    "marcos",
    "rocio"
  ],
  "total": 0
}
```
