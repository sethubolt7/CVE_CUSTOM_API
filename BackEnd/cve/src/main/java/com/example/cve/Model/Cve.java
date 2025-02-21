package com.example.cve.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Entity

public class Cve {

    @Id
    @Column(name = "CVE ID", unique = true)
    String cveID;

    @Column(name = "IDENTIFIER")
    String sourceIdentifier;

    @Column(name = "PUBLISHED DATE")
    LocalDate published;

    @Column(name = "LAST MODIFIED DATE")
    LocalDate lastModified;

    @Column(name = "STATUS")
    String vulnStatus;

    @Column(name = "SCORE")
    Double baseScore;

    public Cve() {
    }

    public Cve(String cveID,LocalDate lastModified, LocalDate published, String vulnStatus, Double baseScore,String sourceIdentifier) {
        this.cveID = cveID;
        this.sourceIdentifier = sourceIdentifier;
        this.lastModified = lastModified;
        this.published = published;
        this.vulnStatus = vulnStatus;
        this.baseScore = baseScore;
    }

    public String getCveID() {
        return cveID;
    }

    public void setCveID(String cveID) {
        this.cveID = cveID;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    public String getVulnStatus() {
        return vulnStatus;
    }

    public void setVulnStatus(String vulnStatus) {
        this.vulnStatus = vulnStatus;
    }

    public Double getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(Double baseScore) {
        this.baseScore = baseScore;
    }
}

