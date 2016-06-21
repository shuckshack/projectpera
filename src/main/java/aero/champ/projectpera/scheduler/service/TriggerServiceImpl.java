package aero.champ.projectpera.scheduler.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aero.champ.projectpera.scheduler.models.Schedule;

@Service
@Transactional
public class TriggerServiceImpl implements TriggerService{

	@Autowired
	private MongoOperations mo;
	
	@Override
	public String getCronExpression(String scheduleCode) {
		Schedule s = mo.findOne(Query.query(Criteria.where("scheduleCode").is(scheduleCode)),Schedule.class);
		
		if(s==null){
			throw new IllegalStateException(scheduleCode+" not defined in schedules");
		}
		
		return s.getCronValue();
	}

}
