package com.jira.restapi.service;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpResponse;
import com.jira.restapi.domain.Board;
import com.jira.restapi.domain.BoardResponse;
import com.jira.restapi.domain.Client;
import com.jira.restapi.domain.ProjectResponse;
import com.jira.restapi.domain.Project;
import com.jira.restapi.domain.Relation;

@Controller
public class JiraStatsControllerImplTesting {

  @Autowired	
  private Client client;

  private static ObjectMapper objectMapper = new ObjectMapper();

  private static HttpResponse httpResponse;
  private static JsonReader reader;

  
  @PostConstruct
  public void initilizeClient() {
	    client = new Client("", "");
  }
  

  public void writing(HashMap<String, List<Relation>> map) {
    try {
      File statText = new File("issue-relations.txt");
      FileOutputStream is = new FileOutputStream(statText);
      OutputStreamWriter osw = new OutputStreamWriter(is);
      Writer w = new BufferedWriter(osw);

      w.write("@startuml\n");
      for (Map.Entry<String, List<Relation>> directionalReferenceEntry : map.entrySet()) {
        for (Relation value : directionalReferenceEntry.getValue()) {
          w.write("[" + directionalReferenceEntry.getKey() + "]" + " -> " + "[" + value.issue + "]"
              + " : " + value.relation + "\n");
        }
      }
      w.write("@enduml\n");
      w.close();
    } catch (IOException e) {
      System.err.println("Problem writing to the file issue-relations.txt");
    }
  }

  
  public String test() {
	    System.out.println("here 1");
	    //return "myString_test";
	    String project = "1";//resource.getProject();
	    String board = "3";//resource.getBoard();
	    System.out.println("here 2 : " + project + ", :" + board);
	    
	    return project + board;
  }
  

  public List<String> insertIssues() throws IOException, SQLException {
	  
	  

	    String project = "1";//resource.getProject();
	    String board = "3";//resource.getBoard();
	    //https://go2uaas.atlassian.net/rest/agile/1.0/board/

	    	//"https://go2uaas.atlassian.net/rest/api/2/issue/createmeta"
	    URI uriPR =
	        client.getService().getEndpoint(
	            "https://go2uaas.atlassian.net/rest/agile/1.0/board");

	    
	   JsonObject jsonObject = executeHttpRequest(uriPR);

	    System.out.println(jsonObject.toString());
	  
	  return null;
  }
  
  
  public String testing() throws IOException, SQLException {
	  
	    String project = "ESBEP";
	    String board = "ESBEP BUGS from UAT";

	    URI uriPR =
	        client.getService().getEndpoint(
	            "https://emagine-reality.atlassian.net/rest/agile/1.0/board/");

	    JsonObject jsonObject = executeHttpRequest(uriPR);


	    return jsonObject.toString();
	 	  
  }


  
  public HashMap<String, List<String>> getBoards() throws IOException {
	  	  
	    HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();

	    URI uriPR =
	        client.getService().getEndpoint(
	            "https://emagine-reality.atlassian.net/rest/agile/1.0/board/");

	    JsonObject jsonObject = executeHttpRequest(uriPR);

	    String boardId = "";
	    String currentProject = "";

	    for (JsonValue entry : jsonObject.getJsonArray("values")) {

	      JsonObject location = ((JsonObject) entry).getJsonObject("location");
	      if (location != null) {

	        boardId = ((JsonObject) entry).get("id").toString();
	        if (location.get("projectKey") != null) {
	          currentProject = location.getString("projectKey");
	        }
	      }

	      if (resultMap.get(currentProject) == null) {
	        List<String> boardList = new ArrayList<String>();
	        boardList.add(boardId);
	        resultMap.put(currentProject, boardList);
	      } else {
	        resultMap.get(currentProject).add(boardId);
	      }
	    }
	  
	    return resultMap;
  }
  
  
  
  
  public String getProjects() throws IOException {
	  
	  List<Project> projectList = new ArrayList<Project>();
	  HashMap<String, String> resultMap = new HashMap<String, String>();
	  
	    URI uriPR =
	        client.getService().getEndpoint(
	            "https://emagine-reality.atlassian.net/rest/agile/1.0/board/");

	    JsonObject jsonObject = executeHttpRequest(uriPR);

	    String currentProject = "";
	    for (JsonValue entry : jsonObject.getJsonArray("values")) {

	        JsonObject location = ((JsonObject) entry).getJsonObject("location");
	        if (location != null) {

	        	if (location.get("projectKey") != null) {
	            currentProject = location.getString("projectKey");
	          }
	        }

	        if (resultMap.get(currentProject) == null) {
	          System.out.println(currentProject);
	          projectList.add(new Project(currentProject));
	          resultMap.put(currentProject, "put");
	        }
	    }

	    //System.out.print(objectMapper.writeValueAsString(new ObjectResponse(projectList)));
	    return objectMapper.writeValueAsString(new ProjectResponse(projectList));
}
  

  public String getBoardsForSelectedProject(String project) throws IOException {
	  
	    HashMap<String, List<Board>> resultMap = new HashMap<String, List<Board>>();

	    URI uriPR =
	        client.getService().getEndpoint(
	            "https://emagine-reality.atlassian.net/rest/agile/1.0/board/");

	    JsonObject jsonObject = executeHttpRequest(uriPR);

	    String boardId = "";
	    String currentProject = "";

	    for (JsonValue entry : jsonObject.getJsonArray("values")) {

	      JsonObject location = ((JsonObject) entry).getJsonObject("location");
	      if (location != null) {

	        boardId = ((JsonObject) entry).get("id").toString();
	        if (location.get("projectKey") != null) {
	          currentProject = location.getString("projectKey");
	        }
	      }

	      if (resultMap.get(currentProject) == null) {
	        List<Board> boardList = new ArrayList<Board>();
	        boardList.add(new Board(boardId));
	        resultMap.put(currentProject, boardList);
	      } else {
	        resultMap.get(currentProject).add(new Board(boardId));
	      }
	    }
	    return objectMapper.writeValueAsString(new BoardResponse(resultMap.get(project)));
  }
  
  
  
  public List<String> getIssuesForProject() throws IOException, SQLException {

    String project = "ESBEP";
    String board = "ESBEP BUGS from UAT";

    
    List<String> returnList = new ArrayList<String>();
    
    
    HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();

    URI uriPR =
        client.getService().getEndpoint(
            "https://emagine-reality.atlassian.net/rest/agile/1.0/board/");

    JsonObject jsonObject = executeHttpRequest(uriPR);

    String boardId = "";
    String currentProject = "";

    for (JsonValue entry : jsonObject.getJsonArray("values")) {

      if (!((JsonObject) entry).getString("name").equals(board)) {
        continue;
      }

      JsonObject location = ((JsonObject) entry).getJsonObject("location");
      if (location != null) {

        boardId = ((JsonObject) entry).get("id").toString();
        if (location.get("projectKey") != null) {
          currentProject = location.getString("projectKey");
        }
      }

      if (resultMap.get(currentProject) == null) {
        List<String> boardList = new ArrayList<String>();
        boardList.add(boardId);
        resultMap.put(currentProject, boardList);
      } else {
        resultMap.get(currentProject).add(boardId);
      }
    }

    
    // List<String> issueList = new ArrayList<String>();
    for (String boardFound : resultMap.get(project)) {

      System.out.println("board :" + boardFound);
      uriPR =
          client.getService()
              .getEndpoint(
                  "https://emagine-reality.atlassian.net/rest/agile/1.0/board/" + boardFound
                      + "/issue");

      JsonObject issuesForBoard = executeHttpRequest(uriPR);
      HashMap<String, List<Relation>> issueRelations = new HashMap<String, List<Relation>>();

      String issueKey = "";
      Relation tmpRelation;
      for (JsonValue currentIssue : issuesForBoard.getJsonArray("issues")) {

        if (((JsonObject) currentIssue).getString("key") != null) {

          issueKey = ((JsonObject) currentIssue).getString("key");
          uriPR =
              client.getService().getEndpoint(
                  "https://emagine-reality.atlassian.net/rest/agile/1.0/issue/" + issueKey);

          JsonObject issueObject = executeHttpRequest(uriPR);

          if (issueObject.getJsonObject("fields").getJsonArray("issuelinks") != null) {

            for (JsonValue issueLink : issueObject.getJsonObject("fields").getJsonArray(
                "issuelinks")) {

              String relation = ((JsonObject) issueLink).getJsonObject("type").getString("inward");

              if (((JsonObject) issueLink).getJsonObject("outwardIssue") != null) {
                String relatedIssue =
                    ((JsonObject) issueLink).getJsonObject("outwardIssue").getString("key");

                tmpRelation = new Relation(relation, relatedIssue);
                if (issueRelations.get(issueKey) == null) {
                  List<Relation> newList = new ArrayList<Relation>();
                  newList.add(tmpRelation);
                  issueRelations.put(issueKey, newList);
                } else {
                  issueRelations.get(issueKey).add(tmpRelation);
                }
                System.out.println("issue :[" + issueKey + "] " + ", relation : [" + relation + "]"
                    + ", relatedIssue :[" + relatedIssue + "]");
                
              }
            }
          }
        }
      }
      returnList.add("issueRelationsSize : " + issueRelations.size());
      
      //writing(issueRelations);
      //Runtime.getRuntime().exec("java -jar plantuml.1.2019.0.jar issue-relations.txt");
    }
    return returnList;
  }

  public JsonObject executeHttpRequest(URI uriPR) throws IOException {

    try {
      httpResponse = client.getService().executeURI(uriPR);
    } catch (InterruptedException e) {
      System.out.println("interrupted");
    }

    if (httpResponse != null) {
      reader = Json.createReader(httpResponse.getContent());
      return reader.readObject();
    }
    InputStream stream = new ByteArrayInputStream("".getBytes());
    reader = Json.createReader(stream);
    return reader.readObject();

  }
}
