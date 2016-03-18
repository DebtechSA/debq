---
layout: post
title: Llamador - Aplicación Matrices
---

Manual de usuario v1.0

---

# Contenido

 1. Descripción de la aplicación
 2. Configuración
  * Modificación de nombres
  * Configuración de conectividad
 3. Enviar Número

# Descripción de la aplicación

La aplicación permite la comunicacion y el envío de mensajes a las matrices debtech. Tiene las siguientes funcionalidades:

 * Envío de número a las matrices
 * Borrado de secciones
 * Configuración de las matrices

La aplicación inicia en un navegador web y posee dos pantallas, "Enviar Número" y "Configuración", accesibles desde los botónes del menú principal (ver siguiente figura).

![Figura 1 - Pantalla Principal]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/index.jpg)

# Configuración

![Figura 2 - Configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/config.jpg)

### Modificación de nombres

Los nombres de las matrices y secciones pueden editarse utlizando las opciones "Matriz" y "Secciones", como se ve en la siguiente figura.

![Figura 3 - Modificación de nombres]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/names.jpg)

Se debe cliquear en el botón "Guardar" para guardar los datos. Si los datos fueron ingresados correctamente se tendra la siguiente ventana:

![Figura 4 - Datos Correctos]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/names_ok.jpg)

En caso que exista un error se informará con la siguiente ventana:

![Figura 5 - Datos Incorrectos]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/names_error.jpg)

### Configuración de conectividad

Los datos de conectividad de la matriz deben configurarse en la sección "Configuración" (ver siguiente figura). Al presionar en el botón "Guardar" se guardaran los datos en el servidor.

![Figura 6 - Conectividad]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/connectivity.jpg)

Para enviar la configuración a la matriz, se debe configurar la ip y el puerto actual de la matriz (ver siguiente figura) y luego presionar el botón "Enviar Configuración". En caso de que se haya enviado correctamente o que existan errores en el envío se le indicará a través de una ventana.

![Figura 6 - Enviar configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/connectivity_send.jpg)

IMPORTANTE: Antes de presionar el botón "Enviar Configuración", se debe presionar en el botón "Guardar" para que los cambios queden guardados en la aplicación.

IMPORTANTE: En el caso que se quiera configurar por primera vez y la ip actual de la matriz no se encuentre en el rango de la red local, se deberá conectar directamente a la pc y configurar la pc con una ip fija en el mismo rango.

# Enviar Número

La pantalla para envío de mensajes puede verse en la siguiente figura.

![Figura 9 - Principal]({{ site.url }}{{ site.baseurl }}/assets/user_manual/llamador/index.jpg)

IMPORTANTE: Tener en cuenta que para realizar un envío a una matriz, esta debe estar previamente configurada (ver sección "Configuración de conectividad").

Se debe ingresar el número a mostrar y presionar el botón enviar. Al presionar el botón, el mensaje se enviará a todas las matrices que esten conectadas a la red y previamente configuradas.

El botón "Borrar Sección" sirve para borrar todo el contenido de las matrices.