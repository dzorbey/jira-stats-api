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
public class BoardResponse {

	@JsonProperty("xata")
	private @Valid List<Board> xata = null;

	public BoardResponse(@Valid List<Board> xata) {
		super();
		this.xata = xata;
	}

	public List<Board> getXata() {
		return xata;
	}

	public void setXata(List<Board> xata) {
		this.xata = xata;
	}
}
