package com.unifier.dto;

import java.util.List;
import java.util.Map;

public class MultipleTeamsRequest {

    private Map<String, List<Long>> data;

    public Map<String, List<Long>> getData() {
        return data;
    }

    public void setData(Map<String, List<Long>> data) {
        this.data = data;
    }
}
