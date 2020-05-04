package com.pratech.EpService.EpService.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class JsonObjectConvertor implements AttributeConverter<JSONObject, String> {
    @Override
    public String convertToDatabaseColumn(JSONObject jsonObject) {
        String json;
        try{
            json=jsonObject.toJSONString();
        }catch(NullPointerException e){

            json = "invalid Json";
        }
        return json;
    }

    @Override
    public JSONObject convertToEntityAttribute(String jsonDataAsJson) {
        JSONObject jsonData;
        try{
            JSONParser jsonParser = new JSONParser();
           jsonData = (JSONObject)jsonParser.parse(jsonDataAsJson);
        } catch (ParseException e) {
            jsonData=null;
        }
        return jsonData;
    }
}
