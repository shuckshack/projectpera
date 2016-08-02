package aero.champ.projectpera.reports.datasource;

import java.text.DecimalFormat;
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
		
		if(jrField.getName().equals("totalTime")){
			return  df.format((item.getTimeOut().getTime() -
						item.getTimeIn().getTime())/3600000.00);
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
