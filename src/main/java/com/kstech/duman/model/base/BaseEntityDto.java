package com.kstech.duman.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class BaseEntityDto implements Serializable {
    protected String id;
}
