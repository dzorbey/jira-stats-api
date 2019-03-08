package com.jira.restapi.domain;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement
public class ProjectResponse {

	@JsonProperty("xata")
	private @Valid List<Project> xata = null;

	public ProjectResponse(@Valid List<Project> xata) {
		super();
		this.xata = xata;
	}

	public List<Project> getXata() {
		return xata;
	}

	public void setXata(List<Project> xata) {
		this.xata = xata;
	}
}
