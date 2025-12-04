package com.suctf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/json")
public class JsonController {

    @PostMapping(
            value = "/parse",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> parse(@RequestBody String jsonText) {
        Map<String, Object> result = new HashMap<>();
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        try {
            JSONObject parsed = JSON.parseObject(jsonText);
            result.put("success", true);
            result.put("parsed", parsed);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
}
