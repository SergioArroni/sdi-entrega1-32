package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogsRepository logsRepository;

    public void addlog(Log l) {
        logsRepository.save(l);
    }

}
