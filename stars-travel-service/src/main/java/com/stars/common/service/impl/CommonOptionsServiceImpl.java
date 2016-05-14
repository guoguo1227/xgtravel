package com.stars.common.service.impl;

import com.stars.common.service.CommonOptionsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gim on 15-6-9.
 */
@Service
public class CommonOptionsServiceImpl implements CommonOptionsService {

    Logger logger = Logger.getLogger(CommonOptionsServiceImpl.class);
    public Map<String,List<Object>>  loadAllMetadata() {
        List<Map<String, Object>> datas =  new ArrayList<Map<String,Object>> ();
        Map<String, List<Object>> result = new HashMap<>();
        String key = "type";
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> row = datas.get(i);
            if (result.containsKey(row.get(key))) {
                result.get(row.get(key)).add(row);
            }else {
                List<Object> list = new ArrayList<>();
                list.add(row);
                result.put((String)row.get(key), list);
            }
        }

        return result;
    }


    public List<Object> loadMetaByType(String metaType) {
        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>> ();
        List<Object> rs = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            rs.add(datas.get(i));
        }
        return rs;
    }

}
