package com.example.cve.Repository;

import com.example.cve.Model.Cve;
import com.example.cve.Model.CveDesc;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CveRepository extends JpaRepository<Cve,String> {
}
