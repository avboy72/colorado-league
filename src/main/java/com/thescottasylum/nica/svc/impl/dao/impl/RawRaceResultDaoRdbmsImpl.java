package com.thescottasylum.nica.svc.impl.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thescottasylum.nica.svc.impl.RawRaceResult;
import com.thescottasylum.nica.svc.impl.dao.RawRaceResultDao;

@Repository
public class RawRaceResultDaoRdbmsImpl  extends JdbcDaoSupport  implements RawRaceResultDao{

	private SimpleJdbcInsert _insert = null;
	
	@Inject
	public RawRaceResultDaoRdbmsImpl(DataSource dataSource) throws Exception {
		super.setDataSource(dataSource);
	}
	

	@Override
	protected void initDao() throws Exception {
		super.initDao();

		_insert = new SimpleJdbcInsert(getJdbcTemplate());
		_insert.withTableName("raw_race_result");
		_insert.usingColumns(	"eventId", "groupName",	
				"id" 
				,"place"
				,"number" 
				,"name" 
				,"team" 
				,"grade" 
				,"points" 
				,"wave" 
				,"lap1Str" 
				,"lap2Str" 
				,"lap3Str" 
				,"lap4Str" 
				,"lap1Millis" 
				,"lap2Millis" 
				,"lap3Millis" 
				,"lap4Millis" 
				,"penaltyStr" 
				,"timeStr"
				,"timeMillis"
				,"fastestStr");
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void create(String eventId,  String group,RawRaceResult rrr) {
		Map<String,Object> args = new HashMap<>();
		
		args.put("eventId", eventId);
		args.put("groupName", group);
		args.put("id",rrr.id);
		args.put("place",rrr.place);
		args.put("number" ,rrr.number);
		args.put("name" ,rrr.name);
		args.put("team" ,rrr.team);
		args.put("grade" ,rrr.grade);
		args.put("points" ,rrr.points);
		args.put("wave" ,rrr.wave);
		args.put("lap1Str" ,rrr.lap1Str);
		args.put("lap2Str" ,rrr.lap2Str);
		args.put("lap3Str" ,rrr.lap3Str);
		args.put("lap4Str" ,rrr.lap4Str);
		args.put("penaltyStr" ,rrr.penaltyStr);
		args.put("timeStr",rrr.timeStr);
		args.put("fastestStr",rrr.fastestStr);

		args.put("lap1Millis", rrr.lap1Millis);
		args.put("lap2Millis", rrr.lap2Millis);
		args.put("lap3Millis", rrr.lap3Millis);
		args.put("lap4Millis", rrr.lap4Millis);
		args.put("timeMillis", rrr.timeMillis);
		_insert.execute(args);
		
	}
	
/*
	class RawRaceResultRowMapper implements RowMapper<RawRaceResult>{

	    @Override
	    public Booty mapRow(ResultSet rs, int rowNum) throws SQLException
	    {
	    		Booty result = new Booty();
	    		result.setId(rs.getString("id"));
	    		result.setCandyId(rs.getString("candy_id"));
	    		result.setQuantity(rs.getInt("quantity"));
	    		result.setCreateDate(rs.getTimestamp("create_date"));
	    		result.setUpdateDate(rs.getTimestamp("update_date"));
	    		return result;
	    	}
	}*/
}
