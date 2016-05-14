package com.stars.common.service;

import java.util.List;
import java.util.Map;


public interface CommonOptionsService {

    public Map<String,List<Object>> loadAllMetadata();

    public List<Object> loadMetaByType(String metaType);
}
