package com.canela.service.inversionmgmt.controller;

import com.canela.service.inversionmgmt.model.FundxUser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
@RequestMapping("/fund")
public class FundController {


    @PostMapping
    public ResponseEntity<?> CreateFundPerUser(@RequestBody FundxUser fund){
        try{
            BufferedReader reader;
            String line;
            StringBuilder responseContent = new StringBuilder();
            URL url = new URL("http://localhost:3000/api/prov/inversion/fund");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\"id\": "+fund.getId()+", \"associated_account\": \""+fund.getAssociated_account()+"\", \"value\":"+fund.getValue()+", \"risk\":\""+fund.getRisk()+"\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int status = con.getResponseCode();
            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseContent.toString());

            System.out.println(responseContent.toString());
            return new ResponseEntity<Object>(json,HttpStatus.OK);


        }catch (Exception e){
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
