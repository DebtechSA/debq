---
layout: post
title: Aplicación Matrices - API v1.0.0
---

Manual técnico v1.0

---

# Contenido

 1. Descripción de la aplicación
 2. API
  * Dispositivos
  * Matriz
  * Secciones
  * Configuración
  * Cola
  * Envío de fuentes

# Descripción de la aplicación

La aplicación permite la comunicacion y el envío de mensajes a las matrices debtech. Tiene las siguientes funcionalidades:

 * Configuración de matrices y secciones
 * Configuración de conectividad
 * Envio de archivos de fuentes
 * Envío de mensajes a las matrices
 * Borrado de secciones

# API

### Dispositivos

#### Devices All 

 * Descripcion: Devuelve todos los dispositivos disponibles

 * Url: /devices/all

 * Http Action: Get

 * Respuesta:

    [
      {
        "name":"Nombre del dispositivo",
        "version":"Versión del dispositivo"
      },
      ...
    ]


### Matriz

#### Matrix All 

 * Descripcion: Devuelve todas las matrices guardadas

 * Url: /matrix/all

 * Http Action: Get

 * Respuesta:

    [
      {
        "id": "Id de la matriz",        
        "device":{
          "name":"Nombre del dispositivo",
          "version":"Versión del dispositivo"
        },
        "name": "Nombre de la matriz",
        "width": "Ancho",
        "height": "Alto"
      },
      ...
    ]


#### Matrix Get 

 * Descripcion: Devuelve las matriz especificada

 * Url: /matrix/:id

 * Http Action: Get

 * Respuesta:

    {
      "id": "Id de la matriz",
      "device":{
        "name":"Nombre del dispositivo",
        "version":"Versión del dispositivo"
      },
      "name": "Nombre de la matriz",
      "width": "Ancho",
      "height": "Alto"
    }


#### Matrix Edit 

 * Descripcion: Edita los datos de la matriz especificada

 * Url: /matrix/edit

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "id" : "Id de la matriz",
      "device":{
        "name":"Nombre del dispositivo",
        "version":"Versión del dispositivo"
      },
      "name" : "Nombre",
      "width" : "Ancho",
      "height" : "Alto"
    }

 * Ejemplo:

    {
      "id" : 1,
      "device":{
        "name":"Debtech PC",
        "version":"v1.1"
      },
      "name" : "Matriz Test",
      "width" : 32,
      "height" : 16
    }


#### Matrix Create 

 * Descripcion: Crea una matriz

 * Url: /matrix/create

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
        "name" : "Nombre",
        "device":"Nombre del dispositivo",
        "width" : "Ancho",
        "height" : "Alto"
    }

 * Ejemplo:

    {
      "name" : "Matriz Test",
      "device":"Debtech PC",
      "width" : 32,
      "height" : 16
    }


#### Matrix Delete 

 * Descripcion: Elimina la matriz especificada

 * Url: /matrix/:id

 * Http Action: Delete


#### Matrix Sections 

 * Descripcion: Devuelve todas las secciones asociadas a la matriz especificada

 * Url: /matrix/:id/sections

 * Http Action: Get

 * Respuesta:

    [
      {
        "id": "Id de la sección",
        "matrix": {
          "id": "Id de la matriz",
          "device":{
              "name":"Nombre del dispositivo",
              "version":"Versión del dispositivo"
          },
          "name": "Nombre de la matriz",
          "width": "Ancho",
          "height": "Alto"
        },
        "name": "Nombre de la sección",
        "remoteId": "Id remoto de la sección",
        "initialRow": "Fila inicial",
        "initialColumn": "Columna inicial",
        "endRow": "Fila final",
        "endColumn": "Columna final"
      },
      ...
    ]


#### Matrix Section Create 

 * Descripcion: Crea las secciones especificadas en el array

 * Url: /matrix/:id/sections

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    [
      {
        "name": "Nombre de la sección",
        "remoteId": "Id remoto de la sección",
        "initialRow": "Fila inicial",
        "initialColumn": "Columna inicial",
        "endRow": "Fila final",
        "endColumn": "Columna final"
      },
      ...
    ]

 * Ejemplo:

    [
      {
        "name": "Sección 1",
        "remoteId": 1,
        "initialRow": 0,
        "initialColumn": 0,
        "endRow": 1,
        "endColumn": 255
      },
      {
        "name": "Sección 2",
        "remoteId": 2,
        "initialRow": 2,
        "initialColumn": 0,
        "endRow": 3,
        "endColumn": 255
      }
    ]


#### Matrix Config 

 * Descripcion: Devuelve la configuración asociada a la matriz

 * Url: /matrix/:id/config

 * Http Action: Get

 * Respuesta:

    {
      "id": "Id de la configuración",
      "matrix": {
        "id": "Id de la matriz",
        "device":{
          "name":"Nombre del dispositivo",
          "version":"Versión del dispositivo"
        },
        "name": "Nombre",
        "width": "Ancho",
        "height": "Alto"
      },
      "ip": "Dirección IP",
      "netmask": "Mascara de subred",
      "gateway": "Dirección del Gateway",
      "port": "Puerto",
      "mac": "Dirección MAC"
    }


#### Matrix Config Create 

 * Descripcion: Crea la configuración de la matriz especificada

 * Url: /matrix/:id/config

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "ip": "Dirección IP",
      "netmask": "Mascara de subred",
      "gateway": "Dirección del Gateway",
      "port": "Puerto",
      "mac": "Dirección MAC"
    }

 * Ejemplo:

    {
      "ip" : "172.16.100.30",
      "netmask" : "255.255.255.0",
      "gateway" : "172.16.100.254",
      "port" : 5002,
      "mac" : "2:0:1:1:1:0"
    }


### Secciones

#### Section All 

 * Descripcion: Devuelve todas las secciones guardadas

 * Url: /sections/all

 * Http Action: Get

 * Respuesta:

    [
      {
        "id": "Id de la sección",
        "matrix": {
          "id": "Id de la matriz",
          "device":{
            "name":"Nombre del dispositivo",
            "version":"Versión del dispositivo"
          },
          "name": "Nombre de la matriz",
          "width": "Ancho",
          "height": "Alto"
        },
        "name": "Nombre de la sección",
        "remoteId": "Id remoto de la sección",
        "initialRow": "Fila inicial",
        "initialColumn": "Columna inicial",
        "endRow": "Fila final",
        "endColumn": "Columna final"
      },
      ...
    ]


#### Section Get 

 * Descripcion: Devuelve las sección especificada

 * Url: /sections/:id

 * Http Action: Get

 * Respuesta:

    {
      "id": "Id de la sección",
      "matrix": {
        "id": "Id de la matriz",
        "device":{
          "name":"Nombre del dispositivo",
          "version":"Versión del dispositivo"
        },
        "name": "Nombre de la matriz",
        "width": "Ancho",
        "height": "Alto"
      },
      "name": "Nombre de la sección",
      "remoteId": "Id remoto de la sección",
      "initialRow": "Fila inicial",
      "initialColumn": "Columna inicial",
      "endRow": "Fila final",
      "endColumn": "Columna final"
    }


#### Section Edit 

 * Descripcion: Edita los datos de la sección especificada

 * Url: /sections/edit

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "id": "Id de la sección",
      "name": "Nombre de la sección",
      "remoteId": "Id remoto de la sección",
      "initialRow": "Fila inicial",
      "initialColumn": "Columna inicial",
      "endRow": "Fila final",
      "endColumn": "Columna final"
    }

 * Ejemplo:

    {
      "id": 1,
      "name": "Sección 1",
      "remoteId": 1,
      "initialRow": 0,
      "initialColumn": 0,
      "endRow": 1,
      "endColumn": 255
    }


#### Section Create 

 * Descripcion: Crea una sección

 * Url: /sections/create

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "matrix": "Id de la matriz asociada",
      "name": "Nombre de la sección",
      "remoteId": "Id remoto de la sección",
      "initialRow": "Fila inicial",
      "initialColumn": "Columna inicial",
      "endRow": "Fila final",
      "endColumn": "Columna final"
    }

 * Ejemplo:

    {
      "matrix": 1,
      "name": "Sección 1",
      "remoteId": 1,
      "initialRow": 0,
      "initialColumn": 0,
      "endRow": 1,
      "endColumn": 255
    }


#### Section Delete 

 * Descripcion: Elimina la sección especificada

 * Url: /sections/:id

 * Http Action: Delete


### Configuración

#### Config All 

 * Descripcion: Devuelve todas las configuraciones guardadas

 * Url: /config/all

 * Http Action: Get

 * Respuesta:

    [
      {
        "id": "Id de la sección",
        "matrix": {
          "id": "Id de la matriz",
          "device":{
            "name":"Nombre del dispositivo",
            "version":"Versión del dispositivo"
          },
          "name": "Nombre de la matriz",
          "width": "Ancho",
          "height": "Alto"
        },
        "name": "Nombre de la sección",
        "remoteId": "Id remoto de la sección",
        "initialRow": "Fila inicial",
        "initialColumn": "Columna inicial",
        "endRow": "Fila final",
        "endColumn": "Columna final"
      },
      ...
    ]


#### Config Get 

 * Descripcion: Devuelve la configuración especificada

 * Url: /config/:id

 * Http Action: Get

 * Respuesta:

    {
      "id": "Id de la configuración",
      "matrix": {
        "id": "Id de la matriz", 
        "device":{
          "name":"Nombre del dispositivo",
          "version":"Versión del dispositivo"
        },
        "name": "Nombre",
        "width": "Ancho",
        "height": "Alto"
      },
      "ip": "Dirección IP",
      "netmask": "Mascara de subred",
      "gateway": "Dirección del Gateway",
      "port": "Puerto",
      "mac": "Dirección MAC"
    }


#### Config Edit 

 * Descripcion: Edita los datos de la configuración especificada

 * Url: /config/edit

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "id" : "Id de la configuración",
      "ip": "Dirección IP",
      "netmask": "Mascara de subred",
      "gateway": "Dirección del Gateway",
      "port": "Puerto",
      "mac": "Dirección MAC"
    }

 * Ejemplo:

    {
      "id" : 1,
      "ip" : "172.16.100.30",
      "netmask" : "255.255.255.0",
      "gateway" : "172.16.100.254",
      "port" : 5002,
      "mac" : "2:0:1:1:1:0"
    }


#### Config Create 

 * Descripcion: Crea una configuración

 * Url: /config/create

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "matrix" : "Id de la matriz asociada",
      "ip": "Dirección IP",
      "netmask": "Mascara de subred",
      "gateway": "Dirección del Gateway",
      "port": "Puerto",
      "mac": "Dirección MAC"
    }

 * Ejemplo:

    {
      "matrix" : 1,
      "ip" : "172.16.100.30",
      "netmask" : "255.255.255.0",
      "gateway" : "172.16.100.254",
      "port" : 5002,
      "mac" : "2:0:1:1:1:0"
    }


#### Config Delete 

 * Descripcion: Elimina la configuración especificada

 * Url: /config/:id

 * Http Action: Delete


#### Config Send Serial (Dispositivos Debtech)

 * Descripcion: Envía la configuración por puerto serie

 * Url: /config/send

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "serial" : "Puerto serie",
      "id" : "Id de configuración"
    }

 * Ejemplo:

    {
      "serial" : "/dev/ttyUSB0",
      "id" : 1
    }


#### Config Send Ethernet (Dispositivos Onbon)

 * Descripcion: Envía la configuración por puerto ethernet

 * Url: /config/send

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "ip" : "Dirección IP",
      "port" : "Puerto"
    }

 * Ejemplo:

    {
      "ip" : "172.16.100.30",
      "port" : 5002
    }


### Cola

#### Queue All 

 * Descripcion: Devuelve todos los mensajes en cola

 * Url: /queue/all

 * Http Action: Get

 * Respuesta:

    [
      {
        "id": "Id del mensaje",
        "datetime": "Timestamp",
        "section": {
          "id": "Id de sección",
          "matrix": {
            "id": "Id de matriz",
            "device":{
              "name":"Nombre del dispositivo",
              "version":"Versión del dispositivo"
            },
            "name": "Nombre de matriz",
            "width": "Ancho",
            "height": "Alto"
          },
          "name": "Nombre de sección",
          "remoteId": "Id remoto",
          "initialRow": "Fila inicial",
          "initialColumn": "Columna inicial",
          "endRow": "Fila final",
          "endColumn": "Columna final"
        },
        "message": "Mensaje",
        "status": {
          "id": "Id de estado",
          "name": "Nombre del estado",
          "message": "Mensaje de estado"
        }
      },
      ...
    ]


#### Queue Get 

 * Descripcion: Devuelve un mensaje específico

 * Url: /queue/:id

 * Http Action: Get

 * Respuesta:

    {
      "id": "Id del mensaje",
      "datetime": "Timestamp",
      "section": {
        "id": "Id de sección",
        "matrix": {
          "id": "Id de matriz",
          "device":{
            "name":"Nombre del dispositivo",
            "version":"Versión del dispositivo"
          },
          "name": "Nombre de matriz",
          "width": "Ancho",
          "height": "Alto"
        },
        "name": "Nombre de sección",
        "remoteId": "Id remoto",
        "initialRow": "Fila inicial",
        "initialColumn": "Columna inicial",
        "endRow": "Fila final",
        "endColumn": "Columna final"
      },
      "message": "Mensaje",
      "status": {
        "id": "Id de estado",
        "name": "Nombre del estado",
        "message": "Mensaje de estado"
      }
    }


#### Queue Delete 

 * Descripcion: Elimina el mensaje

 * Url: /queue/:id

 * Http Action: Delete


#### Queue Add 

 * Descripcion: Agreaga un mensaje en la cola

 * Url: /queue/add

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "section" : "Id de sección",
      "data" : "Mensaje"
    }

El mensaje en el campo "data" puede ser un string o un json segun el tipo de mensaje:

Tipo Texto:

    {
      "type" : "F",
      "option" : "Opciones de efecto (q, Q, m, M, u, d)",
      "velocity" : "Velocidad",
      "tiempo" : "Tiempo de espera",
      "text" : "Texto"
    }

Tipo Borrar pantalla:

    {
      "type" : "b"
    }

Tipo Brillo (dispositivos Onbon):

    {
      "type" : "B",
      "value" : "Valor de brillo (0 - 10)"
    }

Tipo Turnero (turnero Debtech):

    {
      "type" : "T",
      "option" : "Acción (s, r, i, d)",
      "turn" : "Número de turno",
      "workspace" : "Número de puesto"
    }

Tipo Contador (contador Debtech):

    {
      "type" : "C",
      "option" : "Acción (i, d)",
      "number" : "Cuenta"
    }

Tipo Grupo:

    {
      "type" : "G",
      "widgets": [{
        "initialRow" : "Fila inicial",
        "initialColumn" : "Columna inicial",
        "endRow" : "Fila final",
        "endColumn" : "Columna final",
        "data" : "Mensaje (también puede ser un json)"
      }, 
      ...]
    }

 * Ejemplo 1:

    {
      "section" : 1,
      "data" : "Ts*N0001*P0001*"
    }

 * Ejemplo 2:

    {
      "section" : 1,
      "data" : {
        "type" : "F",
        "option" : "q",
        "velocity" : 5,
        "time" : 2,
        "text" : "Texto de prueba"
      }
    }

### Envío de fuentes

#### Font Send (Dispositivos Onbon)

 * Descripcion: Envía un archivo de fuente

 * Url: /config/font

 * Http Action: Post

 * Headers:
   * ContentType : "application/json"
   * Accept : "application/json"

 * Body:

    {
      "id" : "Id de matriz",
      "file" : "Archivo de fuente leído como binario",
      "fontId" : "Id de fuente (0, 1 o 2)"
      "width" : "Ancho de fuente",
      "height" : "Alto de fuente"
    }

 * Ejemplo:

    {
      "id" : 1,
      "file" : "",
      "fontId" : 0
      "width" : 16,
      "height" : 16
    }
