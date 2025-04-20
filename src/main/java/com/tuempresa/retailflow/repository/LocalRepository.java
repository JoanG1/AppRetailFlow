package com.tuempresa.retailflow.repository;

import com.tuempresa.retailflow.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {


    List<Local> findByid (Long id);
}
