package com.iem.iemserver.repositories;

import com.iem.iemserver.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByLogin(String login);

}
