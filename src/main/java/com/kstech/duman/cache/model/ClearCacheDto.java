package com.kstech.duman.cache.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClearCacheDto {
    private List<String> services = new ArrayList<>();
}
