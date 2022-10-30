package com.tj.justdoit.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
//@Builder(toBuilder = true)
@Document("tasks")
public class Task {
    @Id String id;
    String text;
    LocalDate date;
    boolean isDone;

//    @JsonCreator
//    public Task(@JsonProperty("text") String text, @JsonProperty("date") LocalDate date) {
//        this.text = text;
//        this.date = date;
//        this.isDone = false;
//    }

    public Task(String text, LocalDate date, boolean isDone) {
        this.text = text;
        this.date = date;
        this.isDone = isDone;
    }


    //todo allargscontructor for testing

}
