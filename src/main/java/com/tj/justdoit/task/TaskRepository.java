package com.tj.justdoit.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    @Query(sort = "{isDone:1}")
    List<Task> findByDate(LocalDate date);

    Optional<Task> findByTextAndDate(String text, LocalDate date);

    @Query(sort = "{date:-1}")
    List<Task> findAllByIsDoneTrue();

    @Query(value = "{date: {$gt:?0}}", sort = "{date:1}")
    List<Task> findAllByDateInFuture(LocalDate date);
}
