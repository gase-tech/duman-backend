package com.kstech.duman.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BaseEntityResource implements Serializable {
    protected String id;
    protected String createdDate;
    protected String lastModifiedDate;
}
