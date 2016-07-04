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

		JRFileVirtualizer virtualizer = new JRFileVirtualizer(2, "tmp");
		try {
			
			EmployeeDetails empDetails = getDummyEmployeeDetails();
			String sourceFileName = "reports\\CurrentPayPeriod.jasper";
			virtualizer.setReadOnly(true);

			TimeInOutDataSource timeInOutDS = new TimeInOutDataSource(empDetails.getTimeInOutList());
			Map<String, Object> parameters = new HashMap<String, Object>();
		    parameters.put("employeeName", empDetails.getFirstName()+" "+empDetails.getLastName());
		    parameters.put("teamLeadName", empDetails.getTeamLeadName());
		    parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		    
		    
		    JasperPrint jasperPrint = JasperFillManager.
		    		fillReport(sourceFileName, parameters, timeInOutDS);
		    
		   
            ByteArrayOutputStream excelReport = new ByteArrayOutputStream();
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
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
		
		
		TimeInOut timeInOut1stDay = new TimeInOut();
		timeInOut1stDay.setTimeIn(new Date());
		timeInOut1stDay.setTimeOut(new Date());
		
		TimeInOut timeInOut2ndDay = new TimeInOut();
		timeInOut2ndDay.setTimeIn(new Date());
		timeInOut2ndDay.setTimeOut(new Date());
		
		List<TimeInOut> timeInOutList = new ArrayList<TimeInOut>();
		timeInOutList.add(timeInOut1stDay);
		timeInOutList.add(timeInOut2ndDay);
		
		empDetails.setTimeInOutList(timeInOutList);
		
		return empDetails;
	}


}
