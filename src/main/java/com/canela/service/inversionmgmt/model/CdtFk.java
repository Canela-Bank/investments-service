package com.canela.service.inversionmgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CdtFk {
    private String user_id;
    private Integer user_document_type;
}
