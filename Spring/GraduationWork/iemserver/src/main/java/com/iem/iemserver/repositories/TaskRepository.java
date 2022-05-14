package com.iem.iemserver.repositories;

import com.iem.iemserver.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    Iterable<Task> findAllByTaskListId(Integer TaskListId);

}