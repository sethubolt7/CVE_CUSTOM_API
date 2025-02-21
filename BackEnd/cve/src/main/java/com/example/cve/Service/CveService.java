package com.example.cve.Service;

import com.example.cve.Model.Cve;
import com.example.cve.Model.CveDesc;
import com.example.cve.Repository.CveDescRepository;
import com.example.cve.Repository.CveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CveService {
    @Autowired
    Cve cve;

    @Autowired
    CveDesc cveDesc;

    @Autowired
    CveRepository repository;

    @Autowired
    CveDescRepository descRepository;

    private static final String URL = "https://services.nvd.nist.gov/rest/json/cves/2.0?resultsPerPage={result}&startIndex={startIndex}";
    public String fetchApi() {

        RestTemplate restTemplate = new RestTemplate();
        int startIndex = 0;
        int resultsPerPage = 2000;
        int totalResults = 10000;
        while (startIndex <= totalResults) {
            try {
                String apiUrl = URL.replace("{result}", String.valueOf(resultsPerPage))
                        .replace("{startIndex}", String.valueOf(startIndex));

                ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> vulnerabilities = (List<Map<String, Object>>) responseBody.get("vulnerabilities");

                for(Map<String, Object> vulnerabilitiesData: vulnerabilities){

                    Map<String, Object> cveData = (Map<String, Object>) vulnerabilitiesData.get("cve");
                    String cveID = (String) cveData.get("id");
                    String sourceIdentifier = (String) cveData.get("sourceIdentifier");
                    LocalDate lastModified = LocalDate.parse(String.valueOf(cveData.get("lastModified")).substring(0, 10));
                    LocalDate published = LocalDate.parse(String.valueOf(cveData.get("published")).substring(0, 10));
                    String vulnStatus = String.valueOf(cveData.get("vulnStatus"));
                    Double baseScore = 0.0;

                    String metricParam;
                    if(((Map<String, Object>)cveData.get("metrics")).get("cvssMetricV2") != null){
                        metricParam = "cvssMetricV2";
                        baseScore = (Double)
                                        ((Map<String, Object>)(
                                                (List<Map<String, Object>>)(
                                                        (Map<String, Object>)cveData.get("metrics")
                                                ).get(metricParam)
                                        ).get(0).get("cvssData")
                                        ).get("baseScore");
                    }
                    else{
                        metricParam = "cvssMetricV31";
                        baseScore = (Double)
                                        ((Map<String, Object>)(
                                            (List<Map<String, Object>>)(
                                                (Map<String, Object>)cveData.get("metrics")
                                                ).get(metricParam)
                                            ).get(0).get("cvssData")
                                        ).get("baseScore");
                    }
                    String descLang = "";
                    String descValue = "";
                    for (Map<String, Object> desc : (List<Map<String, Object>>) cveData.get("descriptions")) {
                        descValue = (String) desc.get("value");
                        descLang = (String) desc.get("lang");
                        cveDesc = new CveDesc();
                        cveDesc.setCveID(cveID);
                        cveDesc.setValue(descValue);
                        cveDesc.setLang(descLang);
                        descRepository.save(cveDesc);
                    }

                    cve = new Cve();
                    cve.setCveID(cveID);
                    cve.setSourceIdentifier(sourceIdentifier);
                    cve.setBaseScore(baseScore);
                    cve.setPublished(published);
                    cve.setLastModified(lastModified);
                    cve.setVulnStatus(vulnStatus);
                    repository.save(cve);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startIndex += resultsPerPage;
        }
        return "Sucessfully Loaded";
    }

    public List<Cve> getCvesByYear(int date) {
        List<Cve> cves = repository.findAll(Sort.by(Sort.Direction.DESC, "published"));  // Fetch all CVE records
        return cves.stream()
                .filter(cve -> cve.getPublished().getYear() == date)
                .collect(Collectors.toList());
    }

    public List<Cve> getCvesModifiedDaysAgo(int days) {
        LocalDate requiredDate = LocalDate.now().minusDays(days);
        List<Cve> cves = repository.findAll(Sort.by(Sort.Direction.DESC, "lastModified"));  // Fetch all CVE records
        return cves.stream()
                .filter(cve -> cve.getLastModified().isEqual(requiredDate))
                .collect(Collectors.toList());
    }

    public List<Cve> getCvesScoreAbove(double score){
        List<Cve> cves = repository.findAll(Sort.by(Sort.Direction.ASC, "baseScore"));  // Fetch all CVE records
        return cves.stream()
                .filter(cve -> cve.getBaseScore()>score)
                .collect(Collectors.toList());
    }

    public Optional<CveDesc> getCveDesc(String cveId) {
        Optional<CveDesc> cveDesc = descRepository.findById(cveId);
        return cveDesc;
    }
}
