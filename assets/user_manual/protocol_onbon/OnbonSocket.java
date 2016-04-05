/*
 * Copyright 2015, Debtech S.R.L.
 * 
 * http://www.debtech.com.ar
 * http://www.debmedia.net
 */

package controllers.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import play.Logger;

/**
 * @class OnbonSocket
 * @brief Onbon Socket model class.
 * @author Diego Fernández
 */
public class OnbonSocket {
    
    private Socket tcpSocket = null;
    private String ip = "172.16.100.30";
    private int port = 5002;
    
    private DataOutputStream out;
    private DataInputStream in;
    
    /**
    * @fn open
    * @brief Opens a socket connection with the device
    * @param ip
    * @param port
    * @throws Exception
    */
    public void open(String ip, int port) throws Exception {
                    
        this.ip = ip;
        this.port = port;

        this.tcpSocket = new Socket();
        this.tcpSocket.connect(new InetSocketAddress(this.ip, this.port), 10000);
        this.tcpSocket.setSoTimeout(10000);
        this.out = new DataOutputStream(this.tcpSocket.getOutputStream());
        this.in = new DataInputStream(this.tcpSocket.getInputStream());
        Logger.debug("[OnbonSocket.open] Socket created. Ip: " + this.ip + " Port: " + this.port);
    }
    
    /**
    * @fn close
    * @brief Closes the socket connection with the device
    */
    public void close() {
        
        try {
            this.tcpSocket.close();
            Logger.debug("[OnbonSocket.close] Socket closed.");
        }
        catch(Exception e) {}
    }
    
    /**
    * @fn send
    * @brief Sends bytes using the OnbonProtocol
    * @param data
    * @throws Exception
    */
    public void send(byte[] data) throws Exception {
        
        byte[] ackMessage = OnbonMessage.getAckMessage();
                
        out.write(data);

        Thread.sleep(50);

        byte[] responseByteArray = new byte[100];
        in.read(responseByteArray);

        if(Arrays.equals(responseByteArray, ackMessage)) {
            throw new Exception("No ACK received.");
        }
         
        Logger.debug("[OnbonSocket.send] Bytes " + new String(data) + " sended. Response: ACK");
    }
    
    /**
    * @fn send
    * @brief Sends a file using the OnbonProtocol
    * @param data
    * @throws Exception
    */
    public void sendFile(byte[] data) throws Exception {
        
        byte[] startWriteFileMessage = OnbonMessage.getStartWriteFileMessage(data);
        byte[] writeFileMessage = OnbonMessage.getWriteFileMessage(data);
                
        send(startWriteFileMessage);                
        send(writeFileMessage);
        
        Logger.debug("[OnbonSocket.send] Message " + new String(data) + " sended. Response: ACK");
    }
}
