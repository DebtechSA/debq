/*
 * Copyright 2015, Debtech S.R.L.
 * 
 * http://www.debtech.com.ar
 * http://www.debmedia.net
 */

package controllers.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @class OnbonMessage
 * @brief Onbon Message class.
 * @author Diego Fernández
 */
public class OnbonMessage 
{
    public static final byte displayModeStatic = 0x01;
    public static final byte displayModeDirectShow = 0x02;
    public static final byte displayModeLeft = 0x03;
    public static final byte displayModeRight = 0x04;
    public static final byte displayModeUp = 0x05;
    public static final byte displayModeDown = 0x06;
    
    private static final byte[] frameHead = new byte[] {
        (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, 
        (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5
    };
    
    private static final byte[] frameTail = new byte[] {
        0x5A
    };
    
    private static final int[] checksumTable = {
        0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
        0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
        0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
        0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
        0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
        0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
        0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
        0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
        0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
        0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
        0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
        0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
        0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
        0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
        0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
        0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
        0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
        0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
        0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
        0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
        0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
        0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
        0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
        0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
        0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
        0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
        0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
        0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
        0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
        0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
        0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
        0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040
    };
    
    /**
    * @fn getAckMessage
    * @brief Method to get ACK message bytes
    * @return
    */
    public static byte[] getAckMessage(){
        
        byte[] data = new byte[] {(byte)0xA0, (byte)0x00, (byte)0x00, 
                                    (byte)0x00, (byte)0x00};
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getNackMessage
    * @brief Method to get NACK message bytes
    * @return
    */
    public static byte[] getNackMessage(){
        
        byte[] data = new byte[] {(byte)0xA0, (byte)0x01, (byte)0x00, 
                                    (byte)0x00, (byte)0x00};
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getStartWriteFileMessage
    * @brief Method to get startWrite message bytes
    * @param file
    * @return
    */
    public static byte[] getStartWriteFileMessage(byte[] file){
        
        byte[] data = new byte[] {(byte)0xA1, (byte)0x05, (byte)0x01, 
            (byte)0x00, (byte)0x00, (byte)0x01, file[1], file[2], 
            file[3], file[4], (byte)0x00, (byte)0x00, (byte)0x00, 
            (byte)0x00};
        
        data[10] = (byte) (file.length & 0x00FF);
        data[11] = (byte) ((file.length & 0xFF00) >> 8);
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getWriteFileMessage
    * @brief Method to get writeFile message bytes
    * @param file
    * @return
    */
    public static byte[] getWriteFileMessage(byte[] file){
        
        byte[] dataTemp = new byte[] {(byte)0xA1, (byte)0x06, (byte)0x01, 
            (byte)0x00, (byte)0x00, file[1], file[2], file[3], file[4], 
            (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};

        dataTemp[12] = (byte) (file.length & 0x00FF);
        dataTemp[13] = (byte) ((file.length & 0xFF00) >> 8);        
                
        byte[] data = new byte[18 + file.length];
        
        System.arraycopy(dataTemp, 0, data, 0, 18);
        
        System.arraycopy(file, 0, data, 18, file.length);
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getDeleteFileMessage
    * @brief Method to get delete file message
    * @param fileId
    * @return
    */
    public static byte[] getDeleteFileMessage(int fileId){
        
        byte[] deleteFileBytes = new byte[] {(byte)0xA1, (byte)0x01, 
            (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00,     
            
            // File name
            (byte)0x50, (byte)0x30, 
            (byte)(fileId/10 + 0x30), (byte)(fileId%10 + 0x30)
        };
                
        return getMessageBytes(deleteFileBytes);
    }
    
    /**
    * @fn getPingMessage
    * @brief Method to get ping message bytes
    * @return
    */
    public static byte[] getPingMessage(){
        
        byte[] data = new byte[] {(byte)0xA2, (byte)0x00, (byte)0x01, 
                                    (byte)0x00, (byte)0x00};
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getResetMessage
    * @brief Method to get reset message bytes
    * @return
    */
    public static byte[] getResetMessage(){
        
        byte[] data = new byte[] {(byte)0xA2, (byte)0x01, (byte)0x01, 
                                    (byte)0x00, (byte)0x00};
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getBrightnessMessage
    * @brief Method to get brightness message bytes
    * @param brightness
    * @return
    */
    public static byte[] getBrightnessMessage(int brightness){
        
        byte[] data = new byte[] {(byte)0xA3, (byte)0x02, (byte)0x01, 
                                (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00};
        
        data[6] = (byte) brightness;
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getMacMessage
    * @brief Method to get mac message bytes
    * @param mac
    * @return
    */
    public static byte[] getMacMessage(String mac){
        
        byte[] data = new byte[] {(byte)0xA4, (byte)0x06, (byte)0x01, 
                                (byte)0x00, (byte)0x00, 
                                Byte.valueOf(mac.split(":")[0]), 
                                Byte.valueOf(mac.split(":")[1]),
                                Byte.valueOf(mac.split(":")[2]),
                                Byte.valueOf(mac.split(":")[3]),
                                Byte.valueOf(mac.split(":")[4]),
                                Byte.valueOf(mac.split(":")[5])
        };
        
        return getMessageBytes(data);
    }
    
    /**
    * @fn getConfigIpMessage
    * @brief Method to get config message bytes
    * @param ip
    * @param netmask
    * @param gateway
    * @param port
    * @return
    */
    public static byte[] getConfigIpMessage(String ip, String netmask, String gateway, int port){
        
        InetAddress ipAddress;
        InetAddress subnetAddress;
        InetAddress gatewayAddress;
        try {
            ipAddress = InetAddress.getByName(ip);        
            byte[] ipBytes = ipAddress.getAddress();
            
            subnetAddress = InetAddress.getByName(netmask);        
            byte[] subnetBytes = subnetAddress.getAddress();
            
            gatewayAddress = InetAddress.getByName(gateway);        
            byte[] gatewayBytes = gatewayAddress.getAddress();
        
            byte[] config = new byte[] {(byte)0xA4, (byte)0x05, (byte)0x01, 
                (byte)0x00, (byte)0x00, (byte)0x02, 

                // IP Adress
                ipBytes[0], ipBytes[1], ipBytes[2], ipBytes[3],

                //Subnet
                subnetBytes[0], subnetBytes[1], subnetBytes[2], subnetBytes[3],

                // Gateway
                gatewayBytes[0], gatewayBytes[1], gatewayBytes[2], gatewayBytes[3],

                //Port
                (byte)(port & 0x00FF), (byte)((port & 0xFF00) >> 8), 

                // Server?
                (byte)0x00, 

                // Server IP
                (byte)0xC0, (byte)0xA8, (byte)0x00, (byte)0x01,

                //Server Port
                (byte)0x71, (byte)0x17,

                //Server Pass
                (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x30,
                (byte)0x30, (byte)0x30, (byte)0x30,

                (byte)0x14, (byte)0x00, (byte)0x42, (byte)0x58, (byte)0x2D, 
                (byte)0x4E, (byte)0x45, (byte)0x54, (byte)0x30, (byte)0x30, 
                (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x31
            };

            return getMessageBytes(config);
        }
        catch (UnknownHostException e) {
            return null;
        }
    }
    
    /**
    * @fn getProgrammFileBytes
    * @brief Method to get programm file bytes
    * @param fileId
    * @param areas
    * @return
    */
    public static byte[] getProgrammFileBytes(int fileId, byte[]... areas){        
        return getProgrammFileBytes(fileId, Arrays.asList(areas));
    }
    
    /**
    * @fn getProgrammFileBytes
    * @brief Method to get programm file bytes
    * @param fileId
    * @param areas
    * @return
    */
    public static byte[] getProgrammFileBytes(int fileId, List<byte[]> areas){
        
        byte[] headBytes = new byte[] {(byte)0x00, 
            
            // File name
            (byte)0x50, (byte)0x30, 
            (byte)(fileId/10 + 0x30), (byte)(fileId%10 + 0x30),   
            
            //File len
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
            
            // Resserved + Display Type + Play Times
            (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x01, 
            
            // Program Life + Program Week
            (byte)0xFF, (byte)0xFF, (byte)0x10, (byte)0x28, (byte)0x16, 
            (byte)0x20, (byte)0x10, (byte)0x28, (byte)0xFF, 
            
            // Resserved + Area Num 
            (byte)0x00, (byte)0x00, (byte)areas.size()
        };
        
        int totalLength = headBytes.length + 4 * areas.size();
        for(byte[] area : areas) {
            totalLength += area.length;
        }
        
        byte[] fileBytes = new byte[totalLength];
        System.arraycopy(headBytes, 0, fileBytes, 0, headBytes.length);
        int bytesCopied = headBytes.length;
        
        for(byte[] area : areas) {
        
            byte[] areaLength = new byte[] {
                (byte) (area.length & 0x00FF), 
                (byte) ((area.length & 0xFF00) >> 8), 
                (byte) ((area.length & 0xFF0000) >> 16), 
                (byte) ((area.length & 0xFF000000) >> 24) 
            };
            
            System.arraycopy(areaLength, 0, fileBytes, bytesCopied, areaLength.length);
            bytesCopied += areaLength.length;
            
            System.arraycopy(area, 0, fileBytes, bytesCopied, area.length);
            bytesCopied += area.length;
        }

        byte[] fileBytesWithCRC = new byte[fileBytes.length + 2];
        
        fileBytes[5] = (byte) (fileBytesWithCRC.length & 0x00FF);
        fileBytes[6] = (byte) ((fileBytesWithCRC.length & 0xFF00) >> 8);
        fileBytes[7] = (byte) ((fileBytesWithCRC.length & 0xFF0000) >> 16);
        fileBytes[8] = (byte) ((fileBytesWithCRC.length & 0xFF000000) >> 24);
          
        System.arraycopy(fileBytes, 0, fileBytesWithCRC, 0, fileBytes.length);    
        fileBytesWithCRC[fileBytes.length] = calcChecksum(fileBytes)[0];
        fileBytesWithCRC[fileBytes.length+1] = calcChecksum(fileBytes)[1];
        
        return fileBytesWithCRC;
    }
    
    /**
    * @fn getAreaDataBytes
    * @brief Method to get area data bytes
    * @param file
    * @param diplayMode
    * @param time
    * @param speed
    * @param initialRow
    * @param initialColumn
    * @param width
    * @param height
    * @return
    */
    public static byte[] getAreaDataBytes(String file, byte diplayMode, int time, int speed, int initialRow, int initialColumn, int width, int height){
        
        byte[] headBytes = new byte[] {(byte)0x00,
        
            // Area X
            (byte)((initialColumn/8) & 0x00FF), (byte)(((initialColumn/8) & 0xFF00) >> 8),              
            
            // Area Y
            (byte)(initialRow & 0x00FF), (byte)((initialRow & 0xFF00) >> 8),   
            
            // Width
            (byte)((width/8) & 0x00FF), (byte)(((width/8) & 0xFF00) >> 8), 
            
            // Height
            (byte)(height & 0x00FF), (byte)((height & 0xFF00) >> 8), 
            
            //  DynamicAreaLoc 
            (byte)0xFF,
        
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x02, 
            
            // Effect (Display Mode) + ExitMode 
            diplayMode, (byte)0x00, 
            
            //Speed + StayTime + DataLen 
            (byte)((9-speed) & 0x00FF), (byte)(time*2 & 0x00FF), (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x00
        };
                
        byte[] areaBytes = new byte[headBytes.length + file.length()];
        
        System.arraycopy(headBytes, 0, areaBytes, 0, headBytes.length);
        System.arraycopy(file.getBytes(), 0, areaBytes, headBytes.length, file.length());
        
        areaBytes[23] = (byte) (file.length() & 0x00FF);
        areaBytes[24] = (byte) ((file.length() & 0xFF00) >> 8);
        areaBytes[25] = (byte) ((file.length() & 0xFF0000) >> 16);
        areaBytes[26] = (byte) ((file.length() & 0xFF000000) >> 24);
        
        return areaBytes;
    }
    
    /**
    * @fn getControllerFileBytes
    * @brief Method to get controller file bytes
    * @param width
    * @param height
    * @return
    */
    public static byte[] getControllerFileBytes(int width, int height){
        
        byte[] fileBytes = new byte[] {(byte)0x01, 
            
            // File name
            (byte)0x43, (byte)0x30, (byte)0x30, (byte)0x30, 
            
            //File len
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
            
            // Controller Name 
            (byte)0x44, (byte)0x45, (byte)0x42, (byte)0x54,             
            (byte)0x45, (byte)0x43, (byte)0x48, (byte)0x30, 
            
            // Address + Baudrate
            (byte)0x01, (byte)0x00, (byte)0x01, 
            
            // Width
            (byte)(width & 0x00FF), (byte)((width & 0xFF00) >> 8), 
            
            // Height
            (byte)(height & 0x00FF), (byte)((height & 0xFF00) >> 8), 
            
            // Color + MirrorMode + OE + DA + RowOrder 
            (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,
            
            // FreqPar + OEWidth +OEAngle + FaultProcessMode + CommTimeout 
            (byte)0x00, (byte)0x0C, (byte)0x00, (byte)0x00, (byte)0x11,
            
            // RunMode + LogMode 
            (byte)0x00, (byte)0x00,

            // Reserve 
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte)0x00,   
            (byte)0x00, (byte)0x00, (byte)0x00
        };
        
        byte[] fileBytesWithCRC = new byte[fileBytes.length + 2];
        
        fileBytes[5] = (byte) (fileBytesWithCRC.length & 0x00FF);
        fileBytes[6] = (byte) ((fileBytesWithCRC.length & 0xFF00) >> 8);
          
        System.arraycopy(fileBytes, 0, fileBytesWithCRC, 0, fileBytes.length);    
        fileBytesWithCRC[fileBytes.length] = calcChecksum(fileBytes)[0];
        fileBytesWithCRC[fileBytes.length+1] = calcChecksum(fileBytes)[1];
        
        return fileBytesWithCRC;
    }
    
    /**
    * @fn getHeader
    * @brief Method to get header bytes
    * @param data
    * @return
    */
    private static byte[] getHeader(byte[] data){
        
        byte[] header = new byte[] {(byte)0xFE, (byte)0xFF, (byte)0x00, 
            (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
            (byte)0x00, (byte)0x00, (byte)0xFE, (byte)0x02, (byte)0x00, 
            (byte)0x00};
        
        header[12] = (byte) (data.length & 0x00FF);
        header[13] = (byte) ((data.length & 0xFF00) >> 8);
        
        return header;
    }
    
    /**
    * @fn getChecksum
    * @brief Method to get checksum bytes
    * @param header
    * @param data
    * @return
    */
    private static byte[] getChecksum(byte[] header, byte[] data){   
        
        int crcTemp = 0;
        
        for (int i = 0; i < header.length; i++) {
            crcTemp = ((crcTemp >> 8 ) ^ checksumTable[(crcTemp ^ header[i]) & 0x00FF]);
        }
        
        for (int i = 0; i < data.length; i++) {
            crcTemp = ((crcTemp >> 8 ) ^ checksumTable[(crcTemp ^ data[i]) & 0x00FF]);
        }
        
        byte[] crc = new byte[] {(byte)(crcTemp & 0x00FF), (byte)((crcTemp & 0xFF00) >> 8)};
        
        return crc;
    }
    
    /**
    * @fn calcChecksum
    * @brief Method to get checksum bytes from a byte array
    * @param data
    * @return
    */
    private static byte[] calcChecksum(byte[] data){   
        
        int crcTemp = 0;
        
        for (int i = 0; i < data.length; i++) {
            crcTemp = ((crcTemp >> 8 ) ^ checksumTable[(crcTemp ^ data[i]) & 0x00FF]);
        }
        
        byte[] crc = new byte[] {(byte)(crcTemp & 0x00FF), (byte)((crcTemp & 0xFF00) >> 8)};
        
        return crc;
    }
    
    /**
    * @fn getMessageBytes
    * @brief Method to get an Onbon message bytes
    * @param data
    * @return
    */
    private static byte[] getMessageBytes(byte[] data) {

        byte[] header = getHeader(data);        
        byte[] checksum = getChecksum(header, data);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
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
            
        }
        catch (IOException e) {}
        
        return outputStream.toByteArray();
    }
}
