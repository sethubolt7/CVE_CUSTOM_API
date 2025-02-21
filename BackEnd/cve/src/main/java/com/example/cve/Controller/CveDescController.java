package com.example.cve.Controller;

import com.example.cve.Model.CveDesc;
import com.example.cve.Service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CveDescController {
    @Autowired
    CveService service;
    @Autowired
    CveDesc cveDesc;

    @GetMapping("/getCveDesc/{cveId}")
    public Optional<CveDesc> getCveDesc(@PathVariable String cveId){
        return service.getCveDesc(cveId);
    }
}
