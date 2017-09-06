package com.thescottasylum.nica.svc.impl;

public class RawRaceResult {
	public String id; //1
	public String place; //2
	public String number; //3
	public String name; //4
	public String team; //5
	public String grade; //6
	public String points; //7
	public String wave; //8
	public String lap1Str; //10
	public Long lap1Millis;
	public String lap2Str; //12
	public Long lap2Millis;
	public String lap3Str; //14
	public Long lap3Millis;
	public String lap4Str; //16
	public Long lap4Millis;
	public String penaltyStr; //17
	public Long penaltyMillis;
	
	public String timeStr; //18
	public Long timeMillis;
	public String fastestStr; //19
	public Long fastestMillis;
	
	public boolean dnf = false;
	
	
	
	public String toString() {
		return "{"+
		"id=" + id +
		",place=" + place +
		",number=" + number +
		",name=" +  name +
		",team=" + team +
		",grade=" +grade +
		",points=" + points +
		",wave=" + wave +
		",lap1Str=" + lap1Str +
		",lap2Str=" + lap2Str +
		",lap3Str=" + lap3Str +
		",lap4Str=" + lap4Str +
		",penaltyStr=" +penaltyStr+
		",timeStr=" + timeStr +
		",timeMillis=" + timeMillis +
		",fastestStr=" +fastestStr + "}";
	}
}
