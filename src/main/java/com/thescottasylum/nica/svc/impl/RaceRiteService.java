package com.thescottasylum.nica.svc.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RaceRiteService {

	private final RestTemplate _restTemplate;
	private final ObjectMapper _objectMapper;

	@Inject
	public RaceRiteService(RestTemplate rest, ObjectMapper objectMapper) {
		_restTemplate = rest;
		_objectMapper = objectMapper;
	}

	public Map<String,List<RawRaceResult>> getAllResults(String eventId) {

		String callback = "a";

		URI uri = URI.create(
				"http://my4.raceresult.com/RRPublish/data/config.php?callback=" + callback + "&eventid=" + eventId);

		String result = _restTemplate.getForObject(uri, String.class);

		// Trim off retarded jquey sheehat
		result = result.substring(callback.length() + 1, result.length() - 2);

		try {
			InitialRequest init = _objectMapper.readValue(result, InitialRequest.class);
			Logger.getLogger(this.getClass()).info(init.key);
			return getAllResults(eventId, init.key ).rawRaceResults;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private Set<String> getGroupNames(String eventId, String key) {

		String callback = "a";
		// String url =
		// "http://my4.raceresult.com/RRPublish/data/list.php?callback=a&eventid="+eventId+
		// "&key="+key"&listname=Result+Lists%7CIndividual+Results+-+ALL&contest=0&r=group&name=%235_Sophomore+Girls"

		String url = "http://my4.raceresult.com/RRPublish/data/list.php?callback="+callback
				+"&eventid=" + eventId + "&key=" + key
				+ "&listname=Result+Lists%7CIndividual+Results+-+ALL&page=results&contest=0&r=leaders&l=0";
		

		URI uri = URI.create(url);

		String result = _restTemplate.getForObject(uri, String.class);

		// Trim off retarded jquey sheehat
		result = result.substring(callback.length() + 1, result.length() - 2);

		try {
			GroupNamesResponse response = _objectMapper.readValue(result, GroupNamesResponse.class);
			return response.names;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private ResultsResponse getAllResults(String eventId, String key) {


		Logger.getLogger(this.getClass()).info( eventId + " " + key );
		String callback = "a";
		String url = "http://my4.raceresult.com/RRPublish/data/list.php?callback="+callback
				+"&eventid=" + eventId + "&key=" + key
				+ "&listname=Result+Lists%7CIndividual+Results+-+ALL&page=results&contest=0";
		

		Logger.getLogger(this.getClass()).info( url );
		
		URI uri = URI.create(url);

		String result = _restTemplate.getForObject(uri, String.class);

		// Trim off retarded jquey sheehat
		result = result.substring(callback.length() + 1, result.length() - 2);

		try {
			return _objectMapper.readValue(result, ResultsResponse.class);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class InitialRequest {
		public String key;
		public HashMap<String, String> contests;
		public ArrayList<Map<String, String>> lists;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ResultsResponse {
		public transient Map<String,List<RawRaceResult>> rawRaceResults = new HashMap<>();

		public void setData(Map<String,List<String[]>> data) {
			
			for( Map.Entry<String,List<String[]>> e : data.entrySet()) {

				List<RawRaceResult> resultsList = new ArrayList<>();
				
				rawRaceResults.put(e.getKey(), resultsList);
				
				Logger.getLogger(this.getClass()).error(e.getKey());
				
				for( String[] arr : e.getValue() ) {
				
					RawRaceResult r = parse(arr);
					if( r == null ) {
						Logger.getLogger(this.getClass()).error(e.getKey() + " Ignoring entry... Error with args " + Arrays.toString(arr));
					}
					else {
						resultsList.add(r);
					}
				}
			}

		}
		
		public String toString() {
			return rawRaceResults.toString();
		}
		

		public static RawRaceResult parse(String[] arr) {

			RawRaceResult r = new RawRaceResult();
			if( arr.length == 20 ) {
				
				r.id = arr[0]; //1
				r.place = arr[1]; //2
				r.number = arr[2]; //3
				r.name = arr[3]; //4
				r.team = arr[4]; //5
				r.grade = arr[5]; //6
				r.points = arr[6]; //7
				r.wave = arr[7]; //8
				r.lap1Str = arr[9]; //10
				r.lap2Str = arr[11]; //12
				r.lap3Str = arr[13]; //14
				r.lap4Str = arr[15]; //16
				r.penaltyStr = arr[16]; //17
				r.timeStr = arr[17]; //18
				r.fastestStr = arr[18]; //19
			}
			else if (arr.length == 15){
				r.id = arr[0]; //1
				r.place = arr[1]; //2
				r.number = arr[2]; //3
				r.name = arr[3]; //4
				r.team = arr[4]; //5
				r.grade = arr[5]; //6
				r.points = arr[6]; //7
				r.wave = arr[7]; //8
				r.lap1Str = arr[8]; //10
				r.lap2Str = arr[9]; //11
				r.lap3Str = arr[10]; //13
				r.lap4Str = arr[11]; //
				r.penaltyStr = arr[12]; //17
				r.timeStr = arr[13]; //18
				r.fastestStr = null;
			}
			else {
				r=null;
			}
			
			
			return r;
		}



	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GroupNamesResponse {

		private transient Set<String> names;

		public void setData(Map<String, String[]> data) {
			names = data.keySet();
		}
	}

}
