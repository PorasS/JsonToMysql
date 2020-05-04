package com.pratech.EpService.EpService.controllers;

import com.pratech.EpService.EpService.models.EmbeddedEpisodes;
import com.pratech.EpService.EpService.models.JsonTestObj;
import com.pratech.EpService.EpService.repository.JsonTestObjRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public JsonTestObj jsonConvertor(@RequestBody JSONObject jsonObj) throws ParseException {

        JsonTestObj jsonTestObj =new JsonTestObj(jsonObj);

        return jsonTestObjRepository.save(jsonTestObj);
    }

    @RequestMapping("/json/get/{id}")
    public JsonTestObj getJsonObject(@PathVariable Long id) throws ParseException {
        //        retreiving an entity which has a json string from db
        Optional<JsonTestObj> jsonTestObj=jsonTestObjRepository.findById(id);
        JsonTestObj jsonObject= jsonTestObj.get();

        JSONObject toset=jsonObject.getJsonData();
        toset.put("name","Katrina");
        ((JSONObject)toset.get("adress")).put("city","pune");
        jsonObject.setJsonData(toset);

        jsonTestObjRepository.save(jsonObject);

        return jsonObject;
    }
}
