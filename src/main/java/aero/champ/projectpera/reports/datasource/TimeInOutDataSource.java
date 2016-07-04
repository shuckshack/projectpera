package aero.champ.projectpera.reports.datasource;

import java.util.Iterator;
import java.util.List;

import aero.champ.projectpera.BO.TimeInOut;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TimeInOutDataSource  implements JRDataSource{

	private final Iterator<TimeInOut> iter;
	
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
