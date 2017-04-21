/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hepgk
 */
public class myutils {

    private static final String MQ_MSG_FILE_DIR = "D:\\Documents\\2017-04-17 CSP-95712\\";

    private static final int COUNTER = 10;

    private static final String MQ_MSG_FILE_SUFFIX = ".txt";

    private static final String AWB_NUMBER_PREFIX = "160-";

    private static final String FWB_START = "FWB/14";

    private static final String AWB_ORIG_DEST = "HKGMNL/";

    private static final String AWB_PCS_WGT_VOL = "T500K45MC0.01";

    private static final String FWB_BODY = "FLT/CX963P/21"
            + "\nRTG/MNLCX"
            + "\nSHP"
            + "\n/SUPER GENE CASS"
            + "\n/1 SUPER STREET"
            + "\n/SUPER HK CITY"
            + "\n/HK"
            + "\nCNE"
            + "\n/SUPER GENE CASS LHR"
            + "\n/1 SUPER STREET"
            + "\n/SUPER CITY"
            + "\n/GB"
            + "\nAGT//0051010/5400"
            + "\n/GLT-CASS"
            + "\n/HONGKONG"
            + "\nCVD/HKD/PP/PP/NVD/NCV/XXX"
            + "\nRTD/1/P500/K45/CQ/W45/R23.00/T1035.00"
            + "\n/NG/UNK"
            + "\n/2/NV/MC0.01"
            + "\nOTH/P/SCC200.00"
            + "\n/P/MYC450.00"
            + "\nPPD/WT1035.00"
            + "\n/OC650.00/CT1685.00"
            + "\nISU/21APR17/HKG"
            + "\nREF/EZYFRHT";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int awbNumber = 727533;
        int maxAwbNumber = 727733;
        File mqMsgFileDir = new File(MQ_MSG_FILE_DIR);
        if (!mqMsgFileDir.exists()) {
            mqMsgFileDir.mkdirs();
        }
        File mqMsgFile;
        StringBuilder fileNameBuilder;
        String awbSerial;
        do {
            awbSerial = createAWBSerial(awbNumber);
            
            fileNameBuilder = new StringBuilder(MQ_MSG_FILE_DIR);
            fileNameBuilder.append(awbSerial);
            fileNameBuilder.append(MQ_MSG_FILE_SUFFIX);

            mqMsgFile = new File(fileNameBuilder.toString());
            try (FileWriter writer = new FileWriter(mqMsgFile)) {
                writer.write(createFWB(awbSerial));
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(myutils.class.getName()).log(Level.SEVERE, null, ex);
            }
            awbNumber++;
        } while (awbNumber < maxAwbNumber);

    }
    
    private static String createAWBSerial(int awbNumber) {
        StringBuilder awbNumberBuilder = new StringBuilder(AWB_NUMBER_PREFIX);
        awbNumberBuilder.append(String.format("%7s", String.valueOf(awbNumber)).replace(' ', '0'));
        awbNumberBuilder.append(awbNumber % 7);
        
        return awbNumberBuilder.toString();
    }

    private static String createFWB(String awbSerial) {
        StringBuilder fwbMessageBuilder = new StringBuilder(FWB_START);
        fwbMessageBuilder.append("\n");
        fwbMessageBuilder.append(awbSerial);
        fwbMessageBuilder.append(AWB_ORIG_DEST);
        fwbMessageBuilder.append(AWB_PCS_WGT_VOL);
        fwbMessageBuilder.append("\n");
        fwbMessageBuilder.append(FWB_BODY);

        return fwbMessageBuilder.toString();
    }

}
