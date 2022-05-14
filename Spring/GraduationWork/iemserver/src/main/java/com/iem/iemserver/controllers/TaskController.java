package com.iem.iemserver.controllers;

import com.iem.iemserver.models.Task;
import com.iem.iemserver.models.TaskList;
import com.iem.iemserver.models.UserTaskList;
import com.iem.iemserver.repositories.TaskListRepository;
import com.iem.iemserver.repositories.TaskRepository;
import com.iem.iemserver.repositories.UserTaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskListRepository taskListRepository;
    @Autowired
    UserTaskListRepository userTaskListRepository;

    @GetMapping("/get_all_the_task_list/{user_id}")
    public @ResponseBody Iterable<TaskList> getAllTaskList
            (@PathVariable("user_id") Integer userId) {
        return taskListRepository.findAllByUserId(userId);
    }

    @GetMapping("/get_task_list/{the_task_list_id}")
    public @ResponseBody Iterable<Task> getTaskList
            (@PathVariable("the_task_list_id") Integer TaskListId){
        return taskRepository.findAllByTaskListId(TaskListId);
    }

    @PostMapping("/add_the_task_list")
    public void addTaskList(@RequestParam String title,
                            @RequestParam Date deadline,
                            @RequestParam Integer userId){
        TaskList taskList = new TaskList();
        taskList.setTitle(title);
        taskList.setDeadline(deadline);
        taskList.setUserId(userId);
        Integer taskListId =
                taskListRepository.save(taskList).getId();
        //
        UserTaskList userTaskList = new UserTaskList();
        userTaskList.setUserId(userId);
        userTaskList.setTaskListId(taskListId);
        userTaskListRepository.save(userTaskList);
    }

    @PostMapping("/add_task")
    public void addTask(@RequestParam String content,
                        @RequestParam Boolean checked,
                        @RequestParam Integer taskListId){
        Task task = new Task();
        task.setContent(content);
        task.setChecked(checked);
        task.setTaskListId(taskListId);
        taskRepository.save(task);
    }

}
