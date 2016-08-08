---
layout: post
title: Unionpel - Cartel indicador de velocidad
---

Manual de usuario v1.0

---

# Contenido

 1. Descripción
 2. Configuración

# Descripción del equipo

El equipo permite la captura de una señal de corriente de 4mA a 20mA de un sensor de velocidad y el cálculo de la velocidad asociada. Tiene las siguientes funcionalidades:

 * Medición de señal 4mA a 20mA.
 * Cálculo y vizualización de velocidad de 3 dígitos.
 * Visualización de datos de meta y gramaje de 3 dígitos.
 * Control remoto para configuración de cero y máximo.
 * Control remoto para configuración de meta y gramaje.

![Figura 1 - Equipo]({{ site.url }}{{ site.baseurl }}/assets/user_manual/unionpel/Equipo.png)

# Configuración

Existen dos controles remotos para la configuración del equipo. El control remoto con ID 0 configura los datos de meta y gramaje. Por otro lado, el control remoto con ID 1 configura los datos de cero y máximo. A continuación se detallan los pasos de configuración:

## Control Remoto 0 - Meta y Gramaje

  1. Presionar ambos botones del control remoto para pasar al modo de configuración de meta. El dato de meta quedará parpadeando.
  2. Con el primer y segundo botón se incrementa y decrementa respectivamente el dato de meta. 
  3. Una vez que se llegue al valor deseado, presionar ambos botones para pasar al modo de configuración de gramaje. EL dato de gramaje quedará parpadeando.
  4. Con el primer y segundo botón se incrementa y decrementa respectivamente el dato de gramaje. 
  5. Una vez que se llegue al valor deseado, presionar ambos botones para finalizar. Se realizará un parpadeo de toda la pantalla indicando que se guardaron los valores configurados y se pasará al modo de funcionamiento normal del equipo.

## Control Remoto 1 - Cero y Máximo

  IMPORTANTE: Para iniciar este modo de configuración es necesario que el sensor de corriente esté entregando el mínimo valor (4mA). 

  1. Al presionar ambos botones del control remoto se pasará al modo de configuración de máximo y se tomará el valor de corriente medido en ese momento como cero. Es decir que si en ese momento se mide una corriente de 4.1mA, se tomará ese valor como velocidad cero. El dato de velocidad quedará parpadeando y se mostrará el texto 'MAX'.
  2. Con el primer y segundo botón se incrementa y decrementa respectivamente el dato de velocidad máxima. 
  3. Una vez que se llegue al valor deseado, presionar ambos botones para finalizar. Se realizará un parpadeo de toda la pantalla indicando que se guardaron los valores configurados y se pasará al modo de funcionamiento normal del equipo.