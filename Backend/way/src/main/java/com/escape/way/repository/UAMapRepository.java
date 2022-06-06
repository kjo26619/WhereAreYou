package com.escape.way.repository;

import com.escape.way.model.UAMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UAMapRepository extends JpaRepository<UAMap, Long> {

}
