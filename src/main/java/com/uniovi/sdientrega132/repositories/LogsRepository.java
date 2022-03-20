package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Log;
import org.springframework.data.repository.CrudRepository;
import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LogsRepository extends CrudRepository<Log, Long> {

}
