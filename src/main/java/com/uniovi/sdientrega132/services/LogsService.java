package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.LogsRepository;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<Log>();
        logsRepository.findAll().forEach(logs::add);
        return logs;
    }

    public void deleteAllLogs() {
        logsRepository.deleteAll();
    }
}
