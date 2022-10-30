package com.tj.justdoit.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    private final TaskRepository taskRepositoryMock = mock(TaskRepository.class);
    private final TaskService taskService = new TaskService(taskRepositoryMock);
    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        tasks = List.of(
                new Task(
                        "Today's task",
                        LocalDate.now(),
                        false
                ),
                new Task(
                        "Today's finished task",
                        LocalDate.now(),
                        true
                ),
                new Task(
                        "Finished old task",
                        LocalDate.of(2022, 10, 1),
                        true
                ),
                new Task(
                        "Finished task",
                        LocalDate.of(2022, 10, 3),
                        true
                ),
                new Task(
                        "Tomorrow's task",
                        LocalDate.now().plusDays(1),
                        false
                )
        );

        when(taskRepositoryMock.findAll()).thenReturn(tasks);
        when(taskRepositoryMock.findByDate(LocalDate.now())).thenReturn(tasks.stream()
                .filter(t -> t.date.equals(LocalDate.now()))
                .sorted(Comparator.comparing(Task::isDone))
                .toList());
        when(taskRepositoryMock.findAllByIsDoneTrue()).thenReturn(tasks.stream()
                .filter(Task::isDone)
                .toList());
        when(taskRepositoryMock.findAllByDateInFuture(LocalDate.now())).thenReturn(tasks.stream()
                .filter(t -> t.getDate().isAfter(LocalDate.now()))
                .toList());
    }

    @Test
    void getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        Assertions.assertEquals(5, allTasks.size());
    }

    @Test
    void addNewTask() {
    }

    @Test
    void changeStatus() {
        var taskId = "1";
        var task = tasks.get(0);

        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(task));

        Assertions.assertFalse(task.isDone);
        taskService.changeStatus(taskId);
        Assertions.assertTrue(task.isDone);
    }

    @Test
    void getTodayTasks() {
        List<Task> todayTasks = taskService.getTodayTasks();
        Assertions.assertEquals(2, todayTasks.size());
        Assertions.assertFalse(todayTasks.get(0).isDone);
    }

    @Test
    void getAllDoneTasks() {
        List<Task> doneTasks = taskService.getAllDoneTasks();
        Assertions.assertEquals(3, doneTasks.size());
    }

    @Test
    void getAllFutureTasks() {
        List<Task> futureTasks = taskService.getFutureTasks();
        Assertions.assertEquals(1, futureTasks.size());
        Assertions.assertEquals("Tomorrow's task", futureTasks.get(0).getText());
    }

    @Test
    void updateTask() {
        var taskId = "1";
        var task = tasks.get(0);

        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(task));

        Task updateTask = taskService.updateTask(taskId, "Another text", LocalDate.of(2022, 10, 15));
        Assertions.assertEquals("Another text", updateTask.getText());
        Assertions.assertEquals("2022-10-15", updateTask.getDate().toString());
    }
}