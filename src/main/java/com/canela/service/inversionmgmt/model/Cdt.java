package com.canela.service.inversionmgmt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Cdt {
    @Id
    private String id;
    private Double value;
    private String start_date;
    private String finish_date;
    private Double rate;
    private Boolean status;
    private String user_id;
    private Integer user_document_type;

    public Cdt(@JsonProperty("id") String id,
               @JsonProperty("value") Double value,
               @JsonProperty("start_date") String start_date,
               @JsonProperty("finish_date") String finish_date,
               @JsonProperty("rate") Double rate,
               @JsonProperty("status") Boolean status,
               @JsonProperty("user_id") String user_id,
               @JsonProperty("user_document_type") Integer user_document_type) {
        this.id = id;
        this.value = value;
        this.start_date = start_date;
        this.finish_date = finish_date;
        this.rate = rate;
        this.status = status;
        this.user_id = user_id;
        this.user_document_type = user_document_type;
    }

    public Cdt() {

    }

    //    public void setCdtFkUserId(String user_id){
//        this.user.setUser_id(user_id);
//    }
//    public String getCdtUserId(){
//        return this.user.getUser_id();
//    }
//    public void setCdtFkUserType(Integer user_document_type){
//        this.user.setUser_document_type(user_document_type);
//    }
//    public Integer getCdtUserType(){
//        return this.user.getUser_document_type();
//    }


}
