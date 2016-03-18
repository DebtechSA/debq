---
layout: post
title: Onbon - Configuración IP
---

Manual de usuario v1.0

---

# Contenido

El siguiente manual explica el procedimiento para configurar los parámetros de conectividad de las matrices Onbon. Este procedimiento debe hacerse por única vez y solo si la matriz Onbon no tiene asignada una dirección IP o esta es desconocida por el usuario.

# Descarga

La aplicación se puede descargar desde el siguiente link: http://www.onbonbx.com/en/download.php?id=141

No requiere ninguna instalación, por lo que solo debe iniciarse ejecutando el archivo "LedshowZK.exe" dentro de la carpeta "LedshowZK2012".

# Pasos de Configuración

Una vez dentro de la aplicación, se debe ingresar al menú de configuración haciendo click en el menú "Setup" -> "Set Screen Parameters" como se indica en la siguiente figura:

![Figura 1 - Menú Configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/button_config.jpg)

Para ingresar se le solicitará previamente una clave. La misma es "888".

![Figura 2 - Password]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/password.jpg)

La ventana de configuración se muestra en la siguiente figura. Seleccionar la pestaña "Network Settings" para acceder a las opciones de conectividad

![Figura 3 - Ventana de Configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/config_window.jpg)

Para realizar la configuración de IP de la matriz, hacer click en el botón "IP Setup". Aparecerá la pantalla de la siguiente figura, en donde se podran ingresar los datos de IP, máscara de red, gateway y puerto.

![Figura 4 -Configuración de IP]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/config_ip.jpg)

IMPORTANTE: Para realizar la configuración de una matriz, la misma debe estar conectada a la red local y no pueden haber otras matrices conectadas. Es decir que si se quieren configurar varias matrices, estas debe ser conectadas y configuradas de a una.

Una vez ingresados los valores de conectividad, presionar en el botón "Setup". Se mostrará una advertencia como la de la siguiente figura, y presionando "OK" se enviará la configuración a la matriz conectada.

![Figura 5 - Mensaje de Configuración]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/config_message.jpg)

Si la matriz fue configurada correctamente se mostrará el mensaje "Sending the Set IP address command succesfully" en la barra de estado.

![Figura 6 - Configuración Correcta]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/config_success.jpg)

En caso contrario, se mostrará el mensaje "Connected to the screen failed!". Se deberá chequear que la matriz este correctamente encendida y conectada a la red, y reintentar.

![Figura 7 - Configuración Fallida]({{ site.url }}{{ site.baseurl }}/assets/user_manual/config_onbon/config_failed.jpg)

# Finalizando la configuración

Cuando la matriz ya se encuentre configurada correctamente utilizando el software Onbon, se deberán cargar los datos de IP y enviar la configuración de pantalla desde la aplicación web de Debtech (ver sección "Configuración de conectividad" del manual "Aplicación Matrices"). Si no se realiza esto, la matriz no podrá ser utilizada desde la aplicación de Debtech.
