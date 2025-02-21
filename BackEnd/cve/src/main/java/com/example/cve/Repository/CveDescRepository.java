package com.example.cve.Repository;

import com.example.cve.Model.CveDesc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CveDescRepository extends JpaRepository<CveDesc,String> {
}
