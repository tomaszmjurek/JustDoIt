package com.tj.justdoit.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void addNewTask(Task task) {
        log.info("Adding new task: {}", task);
        var existing = taskRepository.findByTextAndDate(task.getText(), task.getDate());
        if (existing.isPresent()) {
            throw new IllegalStateException("Task given text and date already exists");
        }
        taskRepository.save(task);
    }

    public Task changeStatus(String taskId) {
        log.info("Changing status of task with taskId {}", taskId);
//        var task = taskRepository.findById(taskId).orElseThrow(() ->
//                new IllegalStateException("Task with given taskId does not exist"));
        var task = getTaskById(taskId);

        task.setDone(!task.isDone());
        taskRepository.save(task);
        log.info("Task id={} status changed to {}", taskId, task.isDone);
        return task;
    }

    public List<Task> getTodayTasks() {
        log.info("Getting tasks for day: {}", LocalDate.now());
        return taskRepository.findByDate(LocalDate.now());
    }

    public void deleteTask(String taskId) {
        log.info("Removing task with id={}", taskId);
        boolean exists = taskRepository.existsById(taskId);
        if (!exists) {
            throw new IllegalStateException("Task with id " + taskId + " does not exist");
        }
        taskRepository.deleteById(taskId);
    }

    public Task getTaskById(String taskId) {
        log.info("Getting task with id {}", taskId);
        var task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new IllegalStateException("Task with id " + taskId + " does not exist");
        }
        return task.get();
    }

    public List<Task> getAllDoneTasks() {
        log.info("Getting all done tasks");
        return taskRepository.findAllByIsDoneTrue();
    }

    public Task updateTask(String taskId, String newText, LocalDate newDate) {
        log.info("Updating task with id={} newText={} newDate={}", taskId, newText, newDate);

        Task task = getTaskById(taskId);

        if (newText != null) {
            task.setText(newText);
        }

        if (newDate != null) {
            task.setDate(newDate);
        }

        taskRepository.save(task);
        return task;
    }

    public List<Task> getFutureTasks() {
        return taskRepository.findAllByDateInFuture(LocalDate.now());
    }
}
