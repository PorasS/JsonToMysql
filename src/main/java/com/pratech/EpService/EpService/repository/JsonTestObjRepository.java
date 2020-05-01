package com.pratech.EpService.EpService.repository;

import com.pratech.EpService.EpService.models.JsonTestObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonTestObjRepository extends JpaRepository<JsonTestObj,Long> {
}
