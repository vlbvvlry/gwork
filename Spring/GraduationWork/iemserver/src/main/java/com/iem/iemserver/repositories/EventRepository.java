package com.iem.iemserver.repositories;

import com.iem.iemserver.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {

    Iterable<Event> findAllByDepartmentId(Integer departmentId);

}
