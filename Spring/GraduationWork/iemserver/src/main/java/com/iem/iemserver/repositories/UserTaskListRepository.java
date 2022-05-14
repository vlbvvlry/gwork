package com.iem.iemserver.repositories;

import com.iem.iemserver.models.UserTaskList;
import org.springframework.data.repository.CrudRepository;

public interface UserTaskListRepository extends CrudRepository<UserTaskList, Integer> {
}
