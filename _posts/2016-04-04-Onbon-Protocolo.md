---
layout: post
title: Onbon - Protocolo
---

Manual técnico v1.0

---

# Contenido

El siguiente manual explica el procedimiento para enviar mensajes a la placa de control Onbon BX-5MK. 

Los detalles del protocolo, tipos de mensajes, contenido, etcétera, están explicados con mas detalles en el manual "BX-5K 5MK Font Controler Protocol.pdf" que se puede descargar del siguiente link: http://www.onbonbx.com/en/download.php?id=141

# 1. Formato del mensaje

La estructura del mensaje Onbon es la siguiente: 

![Protocol Structure]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/protocol_structure.jpg)

 * Frame Head --> Compuesto por 8 bytes 0xA5.
 * Frame Tail --> Compuesto por 1 byte 0x5A. 
 * Packet Header --> Ver sección 1.2
 * Data Field --> Ver sección 1.3
 * Packet Check --> Es el CRC16 del Packet Header y Data Field. Ver sección 1.4.

## 1.1. Formato del dato

![Data Packet]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/data_packet.jpg)

Durante el armado del dato, se deben chequear que no se utilicen los bytes restringidos. Si se encuentra el/los byte/s:

 * 0xA5 --> reemplazar por los bytes 0xA6 0x02
 * 0xA6 --> reemplezar por los bytes 0xA6 0x01.
 * 0x5A --> reemplazar por los bytes 0x5B 0x02.
 * 0x5B --> reemplazar por los bytes 0x5B 0x01.
 * 0xA6 0x02 --> reemplazar por el byte 0xA5.
 * 0xA6 0x01 --> reemplazar por el byte 0xA6.
 * 0x5B 0x02 --> reemplazar por el byte 0x5A.
 * 0x5B 0x01 --> reemplazar por el byte 0x5B.

## 1.2 Packet Header

![Packet Header]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/packet_header.jpg)

Siguiendo la tabla anterior, el header por defecto es:

 * DstAddr --> 0xFE 0xFF
 * SrcAddr --> 0x00 0x80
 * Reserve --> 0x00 0x00 0x00 0x00 0x00 0x00
 * DeviceType --> 0xFE
 * ProtocolVersion --> 0x02

## 1.3 Data Field

Los tipos de mensajes se pueden ver en la sección 7 del manual "BX-5K 5MK Font Controler Protocol". A continuación se explican y se muestran ejemplos de algunos de ellos.

### 1.3.1 Set IP Address

Permite configurar la dirección IP y puerto del dispositivo. Por ejemplo, para configurar la IP 192.168.0.10 y puerto 9000 se deben enviar los siguientes bytes de dato:

 * CmdGroup --> 0xA4
 * Cmd --> 0x05
 * Response --> 0x01
 * Reserved --> 0x00 0x00
 * Connect Mode --> 0x02
 * IP Address --> 0xC0(192) 0xA8(168) 0x00(0) 0x0A(10)
 * SubnetMask --> 0xFF(255) 0xFF(255) 0xFF(255) 0x00(0)
 * Gateway --> 0xC0(192) 0xA8(168) 0x00(0) 0x01(1)
 * Port --> 0x28 0x23 --> 9000 es igual a 0x2328 pero se envía primero el byte menos significativo
 * ServerMode --> 0x00
 * ServerIPAddress --> 0xC0 0xA8 0x00 0x01
 * ServerPort --> 0x71 0x17
 * ServerAccessPassword --> 0x30 0x30 0x30 0x30 0x30 0x30 0x30 0x30
 * HeartBeatInterval --> 0x14 0x00 
 * NetID --> 0x42 0x58 0x2D 0x4E 0x45 0x54 0x30 0x30 0x30 0x30 0x30 0x31

Después del envío de este mensaje, el dispositivo se reinicia y se conecta con la nueva dirección asignada. En el caso que por error se configure mal, deberá ser reconfigurada utilizando el software "LedShow" unicamente.

### 1.3.2 Set MAC Address

Permite configurar la dirección MAC del dispositivo. Por ejemplo, para configurar la dirección 1:2:3:4:5:6:7:8 se deben enviar los siguientes bytes de dato:

 * CmdGroup --> 0xA4
 * Cmd --> 0x06
 * Response --> 0x01
 * Reserved --> 0x00 0x00
 * MAC --> 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08

### 1.3.3 Start Write File y Write File

Son los mensajes que deben enviarse al dispositivo para mostrar un texto (file) en la matriz. Debe enviarse primero el mensaje "Start Write File" con el nombre y longitud del file, y por ultimo el mensaje 'Write File' con el file propiamente dicho.

Se llama 'file' a la estructura de bytes que contiene el texto o imagen a mostrar en la matriz. Esta especificado en el manual "BX-5K 5MK Font Controler Protocol" en la sección 8 (Program File Type).

Como puede verse en su especificación, el 'file' posee en su interior datos de area (AreaData). La matriz puede dividirse en varias secciones y mostrar información diferente. Los datos que se muestran en cada sección son especificados en la estructura 'AreaData' (en el manual ver sección 8.1 'Area data format').

Es decir, el mensaje 'Write File' tendría la siguiente estructura:

![Write File]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/write_file.jpg)

Por ejemplo, para enviar el texto "debtech" se debe enviar los siguientes bytes de dato:

 * CmdGroup --> 0xA1
 * Cmd --> 0x06
 * Response --> 0x01
 * Reserved --> 0x00 0x00
 * FileName --> 0x50 0x30 0x30 0x31 --> "P001"
 * LastBlockFlag --> 0x01
 * BlockNum --> 0x00 0x00 
 * BlockLen --> 0x00 0x00
 * BlockAddr --> 0x00 0x00 0x00 0x00
 * Data
   * FileType --> 0x00
   * FileName --> 0x50 0x30 0x30 0x31 --> "P001"
   * FileLen --> 0x40 0x00 0x00 0x00 --> Longitud de 'Data' (primeros los bytes menos significativos)
   * Reserved --> 0xFF
   * DisplayType --> 0x00 0x00
   * PlayTimes --> 0x01
   * ProgramLife --> 0xFF 0xFF 0x10 0x28 0x16 0x20 0x10 0x28 --> Full Time
   * ProgramWeek --> 0xFF
   * Reserved --> 0x00
   * AreaNum --> 0x00
   * AreaDataLen0 --> 0x22 0x00 0x00 0x00 --> Longitud del 'AreaData' (primeros los bytes menos significativos)
   * AreaData0
     * AreaType --> 0x00
     * AreaX --> 0x00 0x00 --> Coordenada X = 0px (Se cuenta de a 8 bits)
     * AreaY --> 0x00 0x00 --> Coordenada Y = 0px (Se cuenta de a 1 bit)
     * AreaWidth --> 0x04 0x00 --> Width = 32px (Se cuenta de a 8 bits)
     * AreaHeight --> 0x20 0x00 --> Height = 32px (Se cuenta de a 1 bit)
     * DynamicAreaLoc --> 0xFF
     * Lines_sizes --> 0x00
     * RunMode --> 0x00
     * Timeout --> 0x00 0x00
     * Reserved --> 0x00 0x00 0x00
     * SingleLine --> 0x01
     * NewLine --> 0x02
     * DisplayMode --> 0x01 --> Texto quieto
     * ExitMode --> 0x00
     * Speed --> 0x00
     * StayTime --> 0x00
     * DataLen --> 0x07 0x00 0x00 0x00 --> Longitud del texto (primeros los bytes menos significativos)
     * Data --> 0x64(d) 0x65(e) 0x62(b) 0x74(t) 0x65(e) 0x63(c) 0x68(h) --> Texto (debtech)
   * Crc --> 0x00 0x00

# 2. Ejemplos en Java

Las clases completas pueden descargarse de los siguientes link:

[OnbonMessage.java]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/OnbonMessage.java)

[OnbonSocket.java]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/OnbonSocket.java)

[MatrixProtocol.java]({{ site.url }}{{ site.baseurl }}/assets/user_manual/protocol_onbon/MatrixProtocol.java)

'OnbonMessage' es la clase que arma la estructura de datos a nivel de bits. Es utilizada por la clase 'MatrixProtocol' para armar un mensaje que muestra un texto en pantalla. La clase 'OnbonSocket' es el que realiza la secuencia de envío de mensajes al dispositivo a través de un socket tcp.

## 2.1. Armado del mensaje

	static byte[] getMessageBytes(byte[] data) {

        byte[] header = getHeader(data);        
        byte[] checksum = getChecksum(header, data);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        outputStream.write(frameHead);
        
        for (int i = 0; i < header.length; i++) {
            if(header[i] == (byte)0xA5) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x02});
            } else if(header[i] == (byte)0xA6) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x01});
            } if(header[i] == (byte)0x5A) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x02});
            } else if(header[i] == (byte)0x5B) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x01});
            } else {
                outputStream.write(new byte[] {header[i]});
            }
        }

        for (int i = 0; i < data.length; i++) {
            if(data[i] == (byte)0xA5) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x02});
            } else if(data[i] == (byte)0xA6) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x01});
            } if(data[i] == (byte)0x5A) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x02});
            } else if(data[i] == (byte)0x5B) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x01});
            } else {
                outputStream.write(new byte[] {data[i]});
            }
        }

        for (int i = 0; i < checksum.length; i++) {
            if(checksum[i] == (byte)0xA5) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x02});
            } else if(checksum[i] == (byte)0xA6) {
                outputStream.write(new byte[] {(byte)0xA6, (byte)0x01});
            } if(checksum[i] == (byte)0x5A) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x02});
            } else if(checksum[i] == (byte)0x5B) {
                outputStream.write(new byte[] {(byte)0x5B, (byte)0x01});
            } else {
                outputStream.write(new byte[] {checksum[i]});
            }
        }
        
        outputStream.write(frameTail);
        
        return outputStream.toByteArray();
    }

Aclaraciones:

 * El atributo pasado como 'data' es un array de bytes correspondiente al mensaje que se quiere enviar ('Data Field').
 * 'frameHead' y 'frameTail' son los array de bytes del header (8 bytes 0xA5) y del tail (1 byte 0x5A) del protocolo.
 * El método 'getHeader' obtiene el header del mensaje ('Packet Header') a partir del dato.
 * El método 'getChecksum' obtiene el crc del mensaje ('Packet Check') a partir del dato.
 * Las implementaciónes con 'for' son las que revisan que no aparezcan bytes protegidos en el packet header, data y checksum.

## 2.2 Envío del mensaje

   	public void sendFile(byte[] data) throws Exception {        
        byte[] startWriteFileMessage = OnbonMessage.getStartWriteFileMessage(data);
        byte[] writeFileMessage = OnbonMessage.getWriteFileMessage(data);                
        send(startWriteFileMessage);                
        send(writeFileMessage);
    }

Aclaraciones:

 * El atributo pasado como 'data' es un array de bytes correspondiente al mensaje que se quiere enviar ('Data Field').
 * Se envía un mensaje del tipo "Start Write" y luego un "Write File".