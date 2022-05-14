package com.iem.iemserver.repositories;

import com.iem.iemserver.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}
