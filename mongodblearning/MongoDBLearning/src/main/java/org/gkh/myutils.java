/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hepgk
 */
public class myutils {

    private static final String MQ_MSG_FILE_DIR = "D:\\Documents\\2017-04-17 CSP-95712\\AWB\\";

    private static final int COUNTER = 10;

    private static final String MQ_MSG_FILE_SUFFIX = ".txt";

    private static final String AWB_NUMBER_PREFIX = "160-";

    private static final String FWB_START = "FWB/14\n";

    private static final String FWB_BODY = "HKGLHR/T500K45MC0.01\n"
            + "FLT/CX4202/24\n"
            + "RTG/LHRCX\n"
            + "SHP\n"
            + "/SUPER GENE CASS\n"
            + "/1 SUPER STREET\n"
            + "/SUPER HK CITY\n"
            + "/HK\n"
            + "CNE\n"
            + "/SUPER GENE CASS LHR\n"
            + "/1 SUPER STREET\n"
            + "/SUPER CITY\n"
            + "/GB\n"
            + "AGT//4200999/0002\n"
            + "/SUPER GENE CASS\n"
            + "/SUPER HK CITY\n"
            + "CVD/HKD/PP/PP/NVD/NCV/XXX\n"
            + "RTD/1/P500/K45/CQ/W45/R66.20/T2979.00\n"
            + "/NG/CONSOL\n"
            + "/2/NV/MC0.01\n"
            + "OTH/P/SCC45.00\n"
            + "/P/MYC45.00\n"
            + "PPD/WT2979.00\n"
            + "/OC90.00/CT3069.00\n"
            + "ISU/24APR17/HKG\n"
            + "REF/EZYFRHT";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int awbNumber = 730533;
        int maxAwbNumber = 730534;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyy-HHmmss");
        String mqMsgFileDirPath = MQ_MSG_FILE_DIR + format.format(cal.getTime()) + File.separatorChar;
        File mqMsgFileDir = new File(mqMsgFileDirPath);

        if (!mqMsgFileDir.exists()) {
            mqMsgFileDir.mkdirs();
        }
        
        File mqMsgFile;
        StringBuilder fileNameBuilder;
        String awbSerial;
        do {
            awbSerial = createAWBSerial(awbNumber);
            fileNameBuilder = new StringBuilder(mqMsgFileDirPath);
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
        fwbMessageBuilder.append(awbSerial);
        fwbMessageBuilder.append(FWB_BODY);

        return fwbMessageBuilder.toString();
    }

}
