package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Log;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogsRepository extends CrudRepository<Log, Long> {

    @Query("SELECT l FROM Log l ORDER BY l.time DESC ")
    List<Log> findAllLogs();
}
