package com.onekin.featurecloud.controller;

import com.onekin.featurecloud.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class GenericController  {

    @Autowired
    private GenericService genericService;


    @ResponseBody
    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE}, path = "/tangled-features/{featureId}")
    public List<String> getTangledFeaturesIds(@PathVariable(value = "featureId") String featureId) {
        return genericService.getTangledFeaturesIds(featureId);
    }

}
