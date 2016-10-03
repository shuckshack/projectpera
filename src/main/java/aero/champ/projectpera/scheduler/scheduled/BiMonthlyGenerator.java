package aero.champ.projectpera.scheduler.scheduled;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.BO.TimeInOut;
import aero.champ.projectpera.parser.EmployeeTimeRecorder;
import aero.champ.projectpera.reports.datasource.TimeInOutDataSource;

/**
 * The Class BiMonthlyGenerator.
 */
public class BiMonthlyGenerator implements ReportGenerator{

	/** The log. */
	private static final Log LOG = LogFactory.getLog(BiMonthlyGenerator.class);
	
	/** The Constant XLSX_FILE_TYPE. */
	private static final String XLSX_FILE_TYPE = "xlsx";
	
	/** The Constant PDF_FILE_TYPE. */
	private static final String PDF_FILE_TYPE = "pdf";
	
	/** The Constant FILENAME. */
	private static final String FILENAME = "Timesheet";
	
	/** The Constant XLSX_JASPER_PATH. */
	private static final String XLSX_JASPER_PATH = "reports\\CurrentPayPeriod.jasper";
	
	/** The Constant PDF_JASPER_PATH. */
	private static final String PDF_JASPER_PATH = "reports\\Timesheet.jasper";
	
	/** The Constant dumpFolder. */
//	private static String dumpFolder = "////csfsmnl.champ.aero/GROUP_MNL/COMMON/CHAMP_Timesheet";
	private static String dumpFolder = "reports\\";

	
	@Override
	public void generateCutOffReport(List<EmployeeDetails> employeeDetails) {
		System.out.println(System.identityHashCode(this)+": Gets date and retrieves relevant range "+new Date());
			boolean isSuccess = true;
			String today = new SimpleDateFormat("yyyyMMMdd").format(new Date());
			dumpFolder = dumpFolder+"\\"+generateBiMonthlyFolderName();
			
			for(EmployeeDetails empDetail: employeeDetails){
				try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				
			    parameters.put("employeeName", empDetail.getFirstName()+" "+empDetail.getLastName());
			    parameters.put("teamLeadName", empDetail.getTeamLeadName());
			    parameters.put("totalBillHours", "88.0");
			    parameters.put("department_position", empDetail.getDepartment()+"/"+empDetail.getPosition());
			    parameters.put("projectName", empDetail.getProject());
			    
			    JasperPrint excelJasperPrint = JasperFillManager.
			    		fillReport(XLSX_JASPER_PATH, parameters, new TimeInOutDataSource(empDetail.getTimeInOutList()));
			    		    
			    JasperPrint pdfJasperPrint = JasperFillManager.
			    		fillReport(PDF_JASPER_PATH, parameters, new TimeInOutDataSource(empDetail.getTimeInOutList()));
			    
	            ByteArrayOutputStream excelReport = new ByteArrayOutputStream();
	            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
	            xlsxExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,excelJasperPrint);
	            xlsxExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, excelReport);
	            xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);              
	            xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
	            xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            xlsxExporter.exportReport();			
		       	byte[] bytes = excelReport.toByteArray();
		       	String fullName = empDetail.getLastName()+"_"+empDetail.getFirstName();
		       	
	 	       FileUtils.writeByteArrayToFile(new File(
	 	    		   		dumpFolder+"\\"+generateFileName(XLSX_FILE_TYPE, fullName,  today)), bytes);
	 	       
	 	       JasperExportManager.exportReportToPdfFile(
	 	    		   		pdfJasperPrint, dumpFolder+"\\"+generateFileName(PDF_FILE_TYPE, fullName,  today));
	 	       
			}catch (JRException e) {
				LOG.error(e);
				isSuccess = false;
			} catch (IOException e) {
				LOG.error(e);
				isSuccess = false;
		    }   
		} 
	}		
	
	/**
	 * Generate file name.
	 *
	 * @param fileType the file type
	 * @param fullName the full name
	 * @param dateStr the date str
	 * @return the string
	 */
	private String generateFileName(String fileType, String fullName, String dateStr){
		return FILENAME+"-"+fullName+"_"+dateStr+"."+fileType;
	}
	

	/**
	 * Generate bi monthly folder.
	 *
	 * @return the string
	 */
	private String generateBiMonthlyFolderName(){
		String folder = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return folder;
	}
	
	/**
	 * Gets the current cut off date str.
	 *
	 * @return the current cut off date str
	 */
	private String getCurrentCutOffDateStr(){
		//TODO: ADD JODA/15th, 31st, 30th
		return "";
	}


}
