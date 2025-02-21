package com.example.cve.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity

public class CveDesc {
    @Id
    @Column(name = "CVE ID", unique = true)
    String cveID;

    @Column(name = "LANGUAGE")
    String lang;

    @Column(name = "Description", length = 3000)
    String value;

    public CveDesc() {
    }

    public CveDesc(String cveID, String lang, String value) {
        this.cveID = cveID;
        this.lang = lang;
        this.value = value;
    }

    public String getCveID() {
        return cveID;
    }

    public void setCveID(String cveID) {
        this.cveID = cveID;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
