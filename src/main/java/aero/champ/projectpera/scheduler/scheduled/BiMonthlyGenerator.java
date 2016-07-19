package aero.champ.projectpera.scheduler.scheduled;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import aero.champ.projectpera.BO.EmployeeDetails;
import aero.champ.projectpera.BO.TimeInOut;
import aero.champ.projectpera.reports.datasource.TimeInOutDataSource;

public class BiMonthlyGenerator implements ReportGenerator{

	/** The log. */
	private static final Log LOG = LogFactory.getLog(BiMonthlyGenerator.class);
	
	@Override
	public void generateCutOffReport() {
		System.out.println(System.identityHashCode(this)+": Gets date and retrieves relevant range "+new Date());

		try {
			
			EmployeeDetails empDetails = getDummyEmployeeDetails();
			String excelTimesheet = "reports\\CurrentPayPeriod.jasper";
			String pdfTimesheet = "reports\\Timesheet.jasper";
			

			TimeInOutDataSource timeInOutDS = new TimeInOutDataSource(empDetails.getTimeInOutList());

			Map<String, Object> parameters = new HashMap<String, Object>();
		    parameters.put("employeeName", empDetails.getFirstName()+" "+empDetails.getLastName());
		    parameters.put("teamLeadName", empDetails.getTeamLeadName());
		    parameters.put("totalBillHours", String.valueOf((empDetails.getTimeInOutList().size())*8.0));

		    
		    
		    JasperPrint excelJasperPrint = JasperFillManager.
		    		fillReport(excelTimesheet, parameters, timeInOutDS);
		    
		    JasperPrint pdfJasperPrint = JasperFillManager.
		    		fillReport(pdfTimesheet, parameters, new TimeInOutDataSource(empDetails.getTimeInOutList()));
		    
		   
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
		    
 	       try {
 	    	 
 	    	   FileUtils.writeByteArrayToFile(new File("reports\\CurrentPayPeriod.xlsx"), bytes);
 	    	   JasperExportManager.exportReportToPdfFile(pdfJasperPrint, "reports\\Timesheet.pdf");
 	    	  
 	       } catch (IOException e) {
 	    	   e.printStackTrace();
 	       }
 	       
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}		
	

	//FOR TESTING ONLY
	public static void main(String[] args) throws Exception {
		BiMonthlyGenerator biMonthlyGenerator = new BiMonthlyGenerator();
		biMonthlyGenerator.generateCutOffReport();
	}
	
	private static EmployeeDetails getDummyEmployeeDetails(){
		EmployeeDetails empDetails = new EmployeeDetails();
		empDetails.setFirstName("JUAN");
		empDetails.setLastName("DELA CRUZ");
		empDetails.setDate(new Date());
		empDetails.setTeamLeadName("RODRIDO DUTS");
		
		List<TimeInOut> timeInOutList = new ArrayList<TimeInOut>();
		
		for(int i = 0; i < 15; i++){
			TimeInOut timeInOut1stDay = new TimeInOut();
			timeInOut1stDay.setTimeIn(new Date());
			timeInOut1stDay.setTimeOut(new Date());
			timeInOut1stDay.setTotalTime(8.5);
			timeInOutList.add(timeInOut1stDay);
			
		}
		empDetails.setTimeInOutList(timeInOutList);
		
		return empDetails;
	}


}
