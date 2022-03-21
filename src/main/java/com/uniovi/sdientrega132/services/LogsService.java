package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        logsRepository.findAllLogs().forEach(logs::add);
        return logs;
    }

    public void deleteAllLogs() {
        logsRepository.deleteAll();
    }

    public void addLog(Log log){
        logsRepository.save(log);
    }
}
