package com.iem.iemserver.controllers;

import com.iem.iemserver.models.TaskList;
import com.iem.iemserver.models.User;
import com.iem.iemserver.models.UserTaskList;
import com.iem.iemserver.repositories.TaskListRepository;
import com.iem.iemserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RegistrationController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskListRepository taskListRepository;

    @PostMapping("/registration")
    public @ResponseBody void registration(@RequestParam String fullName,
                                           @RequestParam String login,
                                           @RequestParam String password,
                                           @RequestParam short level) {
        User user = new User();
        user.setFullName(fullName);
        user.setLogin(login);
        user.setPassword(password);
        user.setLevel(level);
        Integer userId = userRepository.save(user).getId();
        addTaskList(
                "Ваши задачи",
                new Date(),
                userId
        );
    }

    public void addTaskList(String title,
                            Date deadline,
                            Integer userId){
        TaskList taskList = new TaskList();
        taskList.setTitle(title);
        taskList.setDeadline(deadline);
        taskList.setUserId(userId);
        Integer taskListId =
                taskListRepository.save(taskList).getId();
    }
}
