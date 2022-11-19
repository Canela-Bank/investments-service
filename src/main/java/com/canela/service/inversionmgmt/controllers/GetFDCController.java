package com.canela.service.inversionmgmt.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


//id: inversionManagementService
//uri: http://10.0.0.0:9003/

@RestController
@RequestMapping(value = "/api/inversions")
public class GetFDCController {
	
	 @GetMapping(value = "/getUserFDCS" )
	 @CrossOrigin("*")
	    public ResponseEntity<String> getUserFDCS(@RequestBody fdcsRequest request) {	
		 
		 
		 try {
			 String url = "http://localhost:3001/graphql";
			 String operation = "";
			 String query = "";
			
			 // GraphQL request 
				 CloseableHttpClient client = HttpClientBuilder.create().build();
			        HttpGet requestGraphQL = new HttpGet(url);
			        URI uri = new URIBuilder(requestGraphQL.getURI())
			                .addParameter("query", query)
			                .build();
			        requestGraphQL.setURI(uri);
			        HttpResponse response =  client.execute(requestGraphQL);
			        InputStream inputResponse = response.getEntity().getContent();
			        String actualResponse = new BufferedReader(
			                new InputStreamReader(inputResponse, StandardCharsets.UTF_8))
			                .lines()
			                .collect(Collectors.joining("\n"));

			        final ObjectNode node = new ObjectMapper().readValue(actualResponse, ObjectNode.class);
			        
			        JsonNode fdcs = node.get("data").get(operation);			       
			       
			        // Verify Empty Response  
			        if(fdcs.isEmpty()) {
			        	 return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body("Lo sentimos, hubo un error.");
			        }
			        // Return response 
			        else{
			        	 JsonNode UserFdcs = node.get("data").get(operation);
					        
					     return ResponseEntity.status(HttpURLConnection.HTTP_ACCEPTED).body(UserFdcs.toString());
			        }

		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}	
		 
	 } 
	 
	 static class fdcsRequest {

	     private String userDocument;
	     private int typeDocument;
		public String getUserDocument() {
			return userDocument;
		}
		public void setUserDocument(String userDocument) {
			this.userDocument = userDocument;
		}
		public int getTypeDocument() {
			return typeDocument;
		}
		public void setTypeDocument(int typeDocument) {
			this.typeDocument = typeDocument;
		}
 
	 }
}
