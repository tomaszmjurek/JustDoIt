package com.tj.justdoit.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(path = "/{taskId}")
    public Task getTaskById(@PathVariable("taskId") String taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping(path = "/today")
    public List<Task> getTodayTasks() {
        return taskService.getTodayTasks();
    }

    @GetMapping(path = "/finished")
    public List<Task> getAllDoneTasks() {
        return taskService.getAllDoneTasks();
    }

    @GetMapping(path = "/future")
    public List<Task> getFutureTasks() {
        return taskService.getFutureTasks();
    }

    @PostMapping
    public void addNewTask(@RequestBody Task task) {
        taskService.addNewTask(task);
    }

    @PutMapping(path = "/{taskId}/changeStatus")
    public Task changeStatus(@PathVariable("taskId") String taskId) {
        return taskService.changeStatus(taskId);
    }

    @PutMapping(path = "/{taskId}")
    public Task updateTask(@PathVariable("taskId") String taskId,
                           @RequestParam(required = false) LocalDate date,
                           @RequestParam(required = false) String text) {
        return taskService.updateTask(taskId, text, date);
    }

    @DeleteMapping(path = "/{taskId}")
    public void deleteTask(@PathVariable("taskId") String taskId) {
        taskService.deleteTask(taskId);
    }

}
