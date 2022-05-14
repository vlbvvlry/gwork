package com.iem.iemserver.repositories;

import com.iem.iemserver.models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
}
