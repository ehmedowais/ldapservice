package com.lambdainfo.ldapservice.controller.tasks;


import com.lambdainfo.ldapservice.domain.Tasks;
import com.lambdainfo.ldapservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class TasksController {
    @Autowired
    TodoService tasksService;
    @GetMapping("/tasks")
    public ResponseEntity<Tasks> getAllTasks() throws URISyntaxException {
        var result =  tasksService.getAllTasks();
        if(result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
