package com.canela.service.inversionmgmt.controller;

import com.canela.service.inversionmgmt.model.Cdt;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;


@RestController
@RequestMapping("/api/inversions")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CreateCDTController {
    @PostMapping("/create")
    public ResponseEntity<String> createCdt(@RequestBody Cdt nuevo){
        URL url = null;
      try{
          Random random = new Random();
          String cdtId = String.valueOf(random.nextLong(1000000000L));
          url= new URL("http://localhost:3002/graphql?query=mutation%7B%0A%20%20createTrust(id%3A%22"+cdtId+"%22%2Cvalue%3A"+nuevo.getValue()+"%2Cstart_date%3A%22"+nuevo.getStart_date()+"%22%2Cfinish_date%3A%22"+nuevo.getFinish_date()+"%22%2Crate%3A"+nuevo.getRate()+"%2Cstatus%3A"+nuevo.getStatus()+"%2Cuser_id%3A%22"+nuevo.getUser_id()+"%22%2Cuser_document_type%3A"+nuevo.getUser_document_type()+")%20%7B%0A%20%20%20%20id%0A%20%20%7D%0A%7D");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("POST");
          int response = conn.getResponseCode();
          System.out.println(response);
          if(response == HttpURLConnection.HTTP_OK){
              return ResponseEntity.status(HttpStatus.OK).body("Cdt Creado");
          }
      } catch (MalformedURLException e) {
          throw new RuntimeException(e);
      } catch (ProtocolException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      } catch (Exception e){
          throw new RuntimeException(e);
      }
        return ResponseEntity.status(404).body("No se pudo Crear");
    }

    @GetMapping("/read")
    public ResponseEntity<String> leerCdts(){
        String filename = "mockCDT";
        JSONObject json = null;

        if (!filename.contains(".json")){
            filename += ".json";
        }
        try{
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (inputStream != null){
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bfReader = new BufferedReader(reader);
                String content = null;
                while((content = bfReader.readLine()) != null){
                    sb.append(content);
                }
                bfReader.close();
                json = JSON.parseObject(sb.toString());
            }else{
                return new ResponseEntity("Archivo no existe"+filename, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            return new ResponseEntity("Error de lectura"+e, HttpStatus.BAD_REQUEST);
        }

        JSONArray jsonArray = (JSONArray)json.get("CDT");
        List<Cdt> list = (List<Cdt>)JSONArray.parseArray(jsonArray.toString(),Cdt.class);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
