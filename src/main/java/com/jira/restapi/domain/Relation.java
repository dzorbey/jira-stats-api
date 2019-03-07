package com.jira.restapi.domain;

public class Relation {
  public String relation;
  public String issue;

  public Relation(String relation, String issue) {
    super();
    this.relation = relation;
    this.issue = issue;
  }
}
