--liquibase formatted sql

--changeset sean.scott:1
create table raw_race_result(
	
    eventId varchar(256),
    groupName varchar(256),
	id varchar(256),
	place varchar(256),
	number varchar(256),
	name varchar(256),
	team varchar(256),
	grade varchar(256),
	points varchar(256),
	wave varchar(256),
	lap1Str varchar(256),
	lap1Millis integer,
	lap2Str varchar(256),
	lap2Millis integer,
	lap3Str varchar(256),
	lap3Millis integer,
	lap4Str varchar(256),
	lap4Millis integer,
	penaltyStr varchar(256),
	penaltyMillis integer,
			
	timeStr varchar(256),
	timeMillis integer,
	fastestStr varchar(256),
	fastestMillis integer
);
		

--rollback drop table raw_race_result;