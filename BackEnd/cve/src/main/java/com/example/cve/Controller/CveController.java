package com.example.cve.Controller;


import com.example.cve.Model.Cve;
import com.example.cve.Service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@CrossOrigin
@RestController
public class CveController {
    @Autowired
    CveService service;

    @GetMapping("/fetchApi")
    public String fetchApi(){
        return service.fetchApi();
    }

    @GetMapping("/getCvesByYear/{year}")
    public List<Cve> getCvesByYear(@PathVariable int year){
        return service.getCvesByYear(year);
    }

    @GetMapping("/getCvesModifiedDaysAgo/{days}")
    public List<Cve> getCvesModifiedDaysAgo(@PathVariable int days){
        return service.getCvesModifiedDaysAgo(days);
    }

    @GetMapping("/getCvesScoreAbove/{score}")
    public List<Cve> getCvesScoreAbove(@PathVariable double score){
        return service.getCvesScoreAbove(score);
    }
}
