package com.pratech.EpService.EpService.controllers;

import com.pratech.EpService.EpService.models.EmbeddedEpisodes;
import com.pratech.EpService.EpService.models.JsonTestObj;
import com.pratech.EpService.EpService.repository.JsonTestObjRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/series")
@CrossOrigin("*")
public class EpController {
    private static final Logger logger = LoggerFactory.getLogger(EpController.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JsonTestObjRepository jsonTestObjRepository;

    @GetMapping("/name/{name}")
    public EmbeddedEpisodes getWebSeries(@PathVariable("name") String name){
        return restTemplate.getForObject("http://api.tvmaze.com/singlesearch/shows?q="+name+"&embed=episodes", EmbeddedEpisodes.class);
    }

    @PostMapping("/json/save")
    public JSONObject jsonConvertor(@RequestBody JSONObject map) throws ParseException {

//        converting jsonObject to jsonString
        String jsonString = map.toJSONString();
        logger.info(jsonString);

//        passing jsonString to entity which stores a jsonString to db;
        JsonTestObj jsonTestObj = new JsonTestObj(jsonString);

        //Storing an entity which contains a jsonString to db
        jsonTestObjRepository.save(jsonTestObj);

        return map;
    }

    @RequestMapping("/json/get/{id}")
    public JSONObject getJsonObject(@PathVariable Long id) throws ParseException {
        //        retreiving an entity which has a json string from db
        Optional<JsonTestObj> json= jsonTestObjRepository.findById(id);
        JsonTestObj jsonTestObj1 = json.get();
        String jsonTestObjString = jsonTestObj1.getJsonData();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonTestObjString);
        return jsonObject;
    }
}
