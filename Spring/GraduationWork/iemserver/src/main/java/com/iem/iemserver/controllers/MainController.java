package com.iem.iemserver.controllers;

import com.iem.iemserver.models.Department;
import com.iem.iemserver.models.User;
import com.iem.iemserver.repositories.DepartmentRepository;
import com.iem.iemserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/get_user/{user_id}")
    public @ResponseBody Optional<User> getUser
            (@PathVariable("user_id") Integer userId){
        return userRepository.findById(userId);
    }

    @GetMapping("/get_all_user")
    public @ResponseBody Iterable<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/get_all_department")
    public @ResponseBody Iterable<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    @PostMapping("/add_department")
    public void addDepartment(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Integer companyId) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setCompanyId(companyId);
        departmentRepository.save(department);
    }

}
