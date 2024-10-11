package com.example.spring_app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
    public static String convertJsonToString(Object object) {
        String jsonStr = null;

        try {
            jsonStr = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(jsonStr, e);
        }

        return jsonStr;
    }
}
