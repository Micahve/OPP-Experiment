package com.fontys.opaexperiment.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class DynamicRequestFixedDataRequestTypeDTO {
    @Getter @Setter
    private String dataType;
    @Getter @Setter
    private String requestType;
    @Getter
    private final Map<String, Object> extraFields = new HashMap<>();

    @JsonAnySetter
    public void setExtraField(String key, Object value) {
        extraFields.put(key, value);
    }
}