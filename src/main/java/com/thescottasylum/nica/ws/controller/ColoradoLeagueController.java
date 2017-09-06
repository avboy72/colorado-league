package com.thescottasylum.nica.ws.controller;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thescottasylum.nica.svc.ColoradoLeagueResultsService;

@RestController
@RequestMapping(value = "comtb/")
public class ColoradoLeagueController {

	private final ColoradoLeagueResultsService _colLeagueService;

	@Inject
	public ColoradoLeagueController(ColoradoLeagueResultsService colLeagueService) {
		_colLeagueService = colLeagueService;
	}

    @RequestMapping(value="/events/{id}", method=RequestMethod.GET, consumes={"application/json"}, produces="application/json")
    public String  processEvent(@PathVariable("id") String id) throws InterruptedException {
    	

    	if( id.equals("ALL") ) {
    		String[] ids = new String[] {"81057","60300","61134","62028","62691","63429","80682","59993","61079","61997","62738"};
		for(String s: ids) { _colLeagueService.getResults(s); Logger.getLogger(getClass()).info("Completed " + s); Thread.sleep(3000);}
    	}
    	else {
    		_colLeagueService.getResults(id);
    	}

        return "Processed Event "+ id;
    }

    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String exampleForTestCase(@PathVariable("tenantId") String tenantId) {
        return "Greetings from Spring Boot! "+ tenantId;
    }
}
