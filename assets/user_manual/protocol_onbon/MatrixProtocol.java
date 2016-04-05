/*
 * Copyright 2015, Debtech S.R.L.
 * 
 * http://www.debtech.com.ar
 * http://www.debmedia.net
 */

package controllers.common;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import models.MatrixSections;

/**
 * @class MatrixProtocol
 * @brief MatrixProtocol class.
 * @author Diego Fernández
 */
public class MatrixProtocol {
    
    private static final String separator = "*";    
    private static final String propertySeparator = "/";
    
    private static final String cleanMessage = "b";
    private static final String groupMessage = "G";    
    private static final String textMessage = "F";    
    private static final String turnMessage = "T";    
    private static final String counterMessage = "C";    
    private static final String clockMessage = "L";    
    private static final String chronoMessage = "H";
    private static final String brightnessMessage = "B";
    
    /**
        * @fn getOnbonMessage
        * @brief It creates a matrix protocol message from a received json object
        * @param section
        * @param messageObject
        * @return
        */
    public static List<byte[]> getOnbonMessage(MatrixSections section, JsonNode messageObject) {

        List<byte[]> messageBytes = new ArrayList<byte[]>();
        String type = messageObject.get("type").asText();

        // Mensaje del tipo "texto"
        // Fxy*Texto_a_mostrar*
        if(type.equals(textMessage)) {
            
            int time = 0;
            int velocity = 0;
            String font = "\\FE000";

            int sectionInitialRow = section.getInitialRow() * 8;
            int sectionInitialColumn = section.getInitialColumn();
            int sectionWidth = section.getWidth();
            int sectionHeight = (section.getHeight() + 1) * 8;
            
            if(messageObject.get("time") != null) {   
                time = messageObject.get("time").asInt();
            }
            if(messageObject.get("velocity") != null) {
                velocity = messageObject.get("velocity").asInt();
            }

            if(sectionHeight >= 32) {
                font = "\\FE002";
            }
            else if(sectionHeight >= 24) {
                font = "\\FE001";
            }
            
            byte option = OnbonMessage.displayModeStatic;

            switch(messageObject.get("option").asText().charAt(0)) {
                case 'q':
                case 'Q':
                    option = OnbonMessage.displayModeStatic;
                    break;
                case 'm':
                    option = OnbonMessage.displayModeLeft;
                    break;
                case 'M':
                    option = OnbonMessage.displayModeRight;
                    break;
                case 'u':
                    option = OnbonMessage.displayModeUp;
                    break;
                case 'd':
                    option = OnbonMessage.displayModeDown;
                    break;
            }

            byte[] fileData = OnbonMessage.getProgrammFileBytes(
                    section.getRemoteId(), 
                    OnbonMessage.getAreaDataBytes(
                        font + messageObject.get("text").asText(), option, time, velocity, 
                        sectionInitialRow, sectionInitialColumn, sectionWidth, sectionHeight
                    ));

            byte[] startWriteFileMessage = OnbonMessage.getStartWriteFileMessage(fileData);
            byte[] writeFileMessage = OnbonMessage.getWriteFileMessage(fileData);
            
            messageBytes.add(startWriteFileMessage);
            messageBytes.add(writeFileMessage);
        }
        
        // Mensaje para borrar la sección
        else if(type.equals(cleanMessage)) {            
            /*byte[] deleteFileMessage = OnbonMessage.getDeleteFileMessage(
                    section.getRemoteId());*/
            
            byte[] cleanAreaData = OnbonMessage.getProgrammFileBytes(
                    section.getRemoteId(), OnbonMessage.getAreaDataBytes(" ", 
                            OnbonMessage.displayModeStatic, 0, 0, 
                            section.getInitialRow() * 8, section.getInitialColumn(),
                            section.getWidth(), (section.getHeight() + 1) * 8)
                    );            
            
            byte[] startWriteFileMessage = OnbonMessage.getStartWriteFileMessage(cleanAreaData);
            byte[] writeFileMessage = OnbonMessage.getWriteFileMessage(cleanAreaData);
            
            //messageBytes.add(deleteFileMessage);
            messageBytes.add(startWriteFileMessage);
            messageBytes.add(writeFileMessage);
        }
        
        // Mensaje para setear el brillo de pantalla
        else if(type.equals(brightnessMessage)) {            
            byte[] brightnessMessage = OnbonMessage.getBrightnessMessage(
                    messageObject.get("value").asInt());
            
            messageBytes.add(brightnessMessage);
        }

        return messageBytes;
    }
}
