package com.thescottasylum.nica.svc.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.thescottasylum.nica.svc.ColoradoLeagueResultsService;
import com.thescottasylum.nica.svc.Results;
import com.thescottasylum.nica.svc.impl.dao.RawRaceResultDao;

@Named
public class ColoradoLeagueResultsServiceImpl implements ColoradoLeagueResultsService{

	private final RaceRiteService _raceRateService;
	private final RawRaceResultDao _rawRaceResultDao;
	
	@Inject
	public ColoradoLeagueResultsServiceImpl(RaceRiteService raceRiteService, RawRaceResultDao rawRaceResultDao) {
		_raceRateService= raceRiteService;
		_rawRaceResultDao = rawRaceResultDao;
	}
	@Override
	public Results getResults(String eventId) {
		Map<String,List<RawRaceResult>> results = _raceRateService.getAllResults(eventId);

		//String eventId = "81057";
		for( Map.Entry<String, List<RawRaceResult>> e: results.entrySet() ) {
			for( RawRaceResult rrr : e.getValue() ) {
				enhance(rrr);
				_rawRaceResultDao.create(eventId, e.getKey(), rrr);
			}
		}
		return null;
	}
	
	private void enhance(RawRaceResult r) {

		r.name = StringUtils.removeEnd(r.name, " (PTS LEADER)");
		r.name = StringUtils.removeEnd(r.name, " (LEGACY)");
		
		r.dnf = StringUtils.equals(r.place, "*") ||
				StringUtils.equalsIgnoreCase(r.timeStr, "DNF") ||
				StringUtils.equalsIgnoreCase(r.timeStr, "xx:xx:xx");

		r.timeMillis = timeStrToMillis(r.timeStr);
		r.lap1Millis = timeStrToMillis(r.lap1Str);
		r.lap2Millis = timeStrToMillis(r.lap2Str);
		r.lap3Millis = timeStrToMillis(r.lap3Str);
		r.lap4Millis = timeStrToMillis(r.lap4Str);
		r.penaltyMillis = timeStrToMillis(r.penaltyStr);
		r.fastestMillis = timeStrToMillis(r.fastestStr);
		
	}


	private static final Pattern __timePattern =  Pattern.compile("((?<h>\\d+):)?(?<m>\\d+):(?<s>\\d+)(.(?<f>\\d\\d))?");
	private Long timeStrToMillis(String str) {
		
		if( StringUtils.isBlank(str) 
				|| StringUtils.equals(str, "-")
				|| StringUtils.equalsIgnoreCase(str, "xx:xx:xx") 
				|| StringUtils.equalsIgnoreCase(str, "DNF")
				|| StringUtils.equalsIgnoreCase(str, "DNS")) {
			return null;
		}
		Matcher m = __timePattern.matcher(str);
		if( !m.matches() ) {
			throw new IllegalArgumentException("Invalid time format " + str + " for result " + toString() );
		}
		
		String hours = m.group("h");
		String minutes = m.group("m");
		String seconds = m.group("s");
		String fractionalSeconds = m.group("f");
		
		return ( StringUtils.isBlank(hours) ? 0L : Long.parseLong(hours) * 60 * 60 * 1000 ) +
					  Long.parseLong(minutes) * 60 * 1000 +
					  Long.parseLong(seconds) * 1000 +
					  ( StringUtils.isBlank(fractionalSeconds) ? 0L : Long.parseLong(StringUtils.rightPad(fractionalSeconds,3,'0') ) ) ;
		
	}
}
