package com.jira.restapi.control;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jira.restapi.service.JiraStatsControllerImplTesting;

@RestController
@RequestMapping(value = "/stats", produces = { "application/json" })
@Api(value = "Jira Stats", description = "Paper-Rock-Scissors Game Operations")
public class JiraStatsController {

	@Autowired
	private JiraStatsControllerImplTesting statsService;
	
	/*
	 * Returning an Abstract Response here using polymorphism, 
	 * will be useful if this code to be extended by certain exception handling and matching return objects.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/projects")
	@ApiOperation(value = "getProjectsResponse", notes = "Projects")
	public String play()
			throws Exception {

		
		try {
			return statsService.getProjects();
		} catch (Exception e1) {
			return null;//Utility.error(e1.getMessage());
		}
	}
}