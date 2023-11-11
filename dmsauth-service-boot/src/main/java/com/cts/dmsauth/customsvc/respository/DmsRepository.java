package com.cts.dmsauth.customsvc.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.dmsauth.customsvc.entity.Document;

@Repository
public interface DmsRepository extends JpaRepository<Document, Long>{

}
