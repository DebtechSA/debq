---
layout: post
title: Bingo - Aplicación Matrices
---

Manual de usuario v1.0

---

# Contenido

 1. Descripción de la aplicación
 2. Configuración
  * Modificación de nombres
  * Configuración de conectividad
  * Creación y modificación de textos dinámicos
  * Actualización de tag desde archivo
 3. Envío de mensajes
 4. Textos Dinámicos

# Descripción de la aplicación

La aplicación permite la comunicacion y el envío de mensajes a las matrices debtech. Tiene las siguientes funcionalidades:

 * Envío de mensajes de texto a las matrices
 * Borrado de secciones
 * Configuración de las matrices
 * Creación de textos dinámicos (tags)
 * Envío de textos dinámicos (tags)
 * Actualización de tag por medio de archivo de texto

La aplicación inicia en un navegador web y posee dos pantallas, "Envío de Mensajes" y "Configuración", accesibles desde los botónes del menú principal (ver siguiente figura).

![Figura 1 - Pantalla Principal]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/buttons.jpg)

# Configuración

![Figura 2 - Configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/config.jpg)

### Modificación de nombres

Los nombres de las matrices y secciones pueden editarse utlizando las opciones "Matriz" y "Secciones", como se ve en la siguiente figura.

![Figura 3 - Modificación de nombres]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/names.jpg)

Se debe cliquear en el botón "Guardar" para guardar los datos. Si los datos fueron ingresados correctamente se tendra la siguiente ventana:

![Figura 4 - Datos Correctos]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/names_ok.jpg)

En caso que exista un error se informará con la siguiente ventana:

![Figura 5 - Datos Incorrectos]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/names_error.jpg)

### Configuración de conectividad

Los datos de conectividad de la matriz deben configurarse en la sección "Configuración" (ver siguiente figura). Al presionar en el botón "Guardar" se guardaran los datos en el servidor.

![Figura 6 - Conectividad]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/connectivity.jpg)

Para enviar la configuración a la matriz, se debe configurar la ip y el puerto actual de la matriz (ver siguiente figura) y luego presionar el botón "Enviar Configuración". En caso de que se haya enviado correctamente o que existan errores en el envío se le indicará a través de una ventana.

![Figura 6 - Enviar configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/connectivity_send.jpg)

IMPORTANTE: En el caso que se quiera configurar por primera vez y la ip actual de la matriz no se encuentre en el rango de la red local, se deberá conectar directamente a la pc y configurar la pc con una ip fija en el mismo rango.

### Creación y modificación de textos dinámicos

Los textos dinámicos pueden crearse, editarse y eliminarse desde la sección "Tags". 

En el campo "Nombre" se debe ingresar el texto que identifica al tag, y en el campo "Texto" el contenido de dicho tag.

![Figura 7 - Tags]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/tags.jpg)

### Actualización de tag desde archivo

La aplicación permite seleccionar un tag para que puede actualizarse por medio de un archivo de texto externo. 
En el campo "Tag" debe seleccionarse el texto dinamico a actualizar. En el campo "Frecuencia" se debe ingresar la frecuencia (en segundos) con la que la aplicación debe chequear el archivo. En el campo "Archivo" se debe ingresar la ruta en donde se encuentra el archivo de texto a chequear.

![Figura 8 - Tags]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/thread.jpg)

En el campo "Estado" se puede ver el estado de la tarea que chequea el archivo.

Los estados posibles son:

 * Running: La tarea se encuentra corriendo.
 * Stopped: La tarea se encuentra detenida.

Para actualizar los datos se debe presionar el botón "Guardar/Iniciar" (asegurarse de que la tarea se encuentre en estado detenido).

# Envío de mensajes

La pantalla para envío de mensajes puede verse en la siguiente figura.

![Figura 9 - Principal]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/index.jpg)

IMPORTANTE: Tener en cuenta que para realizar un envío a una matriz, esta debe estar previamente configurada (ver sección "Configuración de conectividad").

Se debe seleccionar la matriz y la sección a la que se desea enviar el mensaje, y una vez seleccionadas, se habilitaran las opciones en la sección "Enviar Texto".

![Figura 10 - Mensajes]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/messages.jpg)

En el campo "Tipo" se debe seleccionar el tipo de efecto que se le quiere dar al texto (quieto, movil, movil invertido, arriba, abajo). 

En el campo "Velocidad" se debe ingresar la velocidad del efecto y en el campo "Tiempo" el tiempo en que permanece estático después de realizar el efecto.

Al presionar el botón enviar, el mensaje se agregará en la cola para su posterior envío.

IMPORTANTE: En el caso que se quiera centrar el texto, hay que hacerlo manualmente agregando espacios delante del texto. Para agregarlos hay que anteponer el caracter '\'. Por ejemplo: "\    Debtech".

El botón "Borrar Sección" sirve para borrar todo el contenido de la sección.

### Cola de mensajes

En la cola de mensajes se agregarán todos los mensajes a enviar. Una vez que los mensajes se envien, se actualizarán sus estados según el resultado del envío.

Los estados pueden ser:

 * Pendiente: El mensaje está pendiente de ser enviado.
 * Mensaje enviado: El mensaje se envió correctamente.
 * Error: Ocurrió un error al intentar enviar el mensaje.

![Figura 11 - Cola de mensajes]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/queue.jpg)

Cada mensaje posee un botón "Borrar" que sirve para quitarlo de la cola y un botón "Reenviar". En el caso de que se deseen borrar todos los mensajes, se debe presionar en el botón "Limpiar".

# Textos Dinámicos

Los textos dinámicos o tags se referencian utilizando el caracter '@'. Es decir que si se quiere enviar un mensaje con el tag 'fecha', se debe enviar el texto '@fecha' (ver siguiente figura).

![Figura 11 - Cola de mensajes]({{ site.url }}{{ site.baseurl }}/assets/user_manual/bingo/tag_message.jpg)

Todos los mensajes que se envíen y posean un tag en su contenido, permanecen en la cola de mensajes y se reenvían cuando el tag cambia de valor.

Por ejemplo: 

Si se crea el tag 'fecha' con el valor '21/10/2015' y se envía el mensaje "Hoy es @fecha", el mensaje enviado será "Hoy es 21/10/2015". 

Este mensaje permanecerá activo en la cola y si en algun momento el tag cambia de valor, ya sea manualmente o automaticamente, se volverá a enviar el mensaje. Es decir si el tag cambia al valor '22/10/2015', se enviará un nuevo mensaje con el texto "Hoy es 22/10/2015".

Para dejar de enviar el mensaje, se lo debe borrar presionando el botón "Quitar".
