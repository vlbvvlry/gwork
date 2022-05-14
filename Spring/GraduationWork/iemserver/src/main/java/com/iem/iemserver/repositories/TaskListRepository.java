package com.iem.iemserver.repositories;

import com.iem.iemserver.models.TaskList;
import org.springframework.data.repository.CrudRepository;

public interface TaskListRepository extends CrudRepository<TaskList, Integer> {

    Iterable<TaskList> findAllByUserId(Integer userId);

}
