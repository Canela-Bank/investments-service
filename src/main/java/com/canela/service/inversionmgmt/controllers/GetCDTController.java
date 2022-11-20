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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//id: inversionManagementService
//uri: http://10.0.0.0:9003/

@RestController
@RequestMapping(value = "/api/investments")
public class GetCDTController {
	
	 @GetMapping(value = "/getUserCDTS/{document}/{typeDocument}")
	    public ResponseEntity<String> getUserCDTS(@PathVariable String document, @PathVariable String typeDocument) {	
		 	 
		 try {
			// GraphQL info 
			 String url = "http://localhost:3001/graphql";
			 String operation = "getTrustsByUser";
			 String query = "query{getTrustsByUser(user_document:\""+document+"\",user_document_type:"+typeDocument+"){\n"
			 		+ "  id\n"
			 		+ "  value\n"
			 		+ "  start_date\n"
			 		+ "  finish_date\n"
			 		+ "  rate\n"
			 		+ "  status\n"
			 		+ "  user_id\n"
			 		+ "  user_document_type\n"
			 		+ "}}";
			
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
			        
			        JsonNode cdts = node.get("data").get(operation);	
			        
			    // Verify Empty Response  
			        if(cdts.isEmpty()) {
			        	return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body("Lo sentimos, hubo un error.");
			        }
			     // Return response 
			        else{
			        	JsonNode UserCDTS = node.get("data").get(operation);
					    return ResponseEntity.status(HttpURLConnection.HTTP_ACCEPTED).body(UserCDTS.toString());
			        }

		} catch (Exception e) {
			throw new RuntimeException(e);			
		}	
	}
	 
	 static class cdtsRequest {

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
