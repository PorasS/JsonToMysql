package com.pratech.EpService.EpService.models;

import com.pratech.EpService.EpService.service.JsonObjectConvertor;
import org.hibernate.annotations.Type;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
public class JsonTestObj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonObjectConvertor.class)
    private JSONObject jsonData;

    public JsonTestObj() {
    }

    public JsonTestObj(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }
}
