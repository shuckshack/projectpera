package aero.champ.projectpera.reports.datasource;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aero.champ.projectpera.BO.TimeInOut;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TimeInOutDataSource  implements JRDataSource{

	private final Iterator<TimeInOut> iter;
	private final DecimalFormat df = new DecimalFormat("#.00");
	
	private TimeInOut item;

	public TimeInOutDataSource(List<TimeInOut> timeInOutList){
		iter = timeInOutList.iterator();
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if(jrField.getName().equals("timeIn")){
			return item.getTimeIn();
		}
		
		if(jrField.getName().equals("timeOut")){
			return item.getTimeOut();
		}
		
		if(jrField.getName().equals("timeInStr")){
			String timeInStr = "";
			if(null != item.getTimeIn()){
				timeInStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeIn());

				if(timeInStr.equals("00:00:00")){
					timeInStr = "";
				}
			}
			return timeInStr;
		}
		
		if(jrField.getName().equals("timeOutStr")){
			String timeOutStr = "";
			if(null != item.getTimeOut()){
				timeOutStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeOut());

				if(timeOutStr.equals("00:00:00")){
					timeOutStr = "";
				}
			}
			return timeOutStr;
		}
				
		if(jrField.getName().equals("totalTime")){
			String totalTime = "0";
			
			if(null != item.getTimeOut() && null != item.getTimeOut()){
				String timeInStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeIn());
				String timeOutStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeOut());
				
				if((!timeInStr.equals("00:00:00")) 
						&& (!timeOutStr.equals("00:00:00"))){
					totalTime =   df.format(((item.getTimeOut().getTime() -
							item.getTimeIn().getTime())/3600000.00)-1.0);
				}
			}
			return totalTime;
		}
		
		if(jrField.getName().equals("billableTime")){
			String billableTime = "";
			
			if(null != item.getTimeOut() && null != item.getTimeOut()){
				String timeInStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeIn());
				String timeOutStr = new SimpleDateFormat("HH:mm:ss").format((Date)item.getTimeOut());
				
				if((!timeInStr.equals("00:00:00")) 
						&& (!timeOutStr.equals("00:00:00"))){
					billableTime =  "8.0";
				}
			}
			return billableTime;
		}
		
		
		
		return null;
	}

	@Override
	public boolean next() throws JRException {
		final boolean hasNext = iter.hasNext();
		
		if(hasNext) {
			item = iter.next();
		}
		
		return hasNext;
	}

}
