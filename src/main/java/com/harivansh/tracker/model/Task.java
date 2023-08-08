package com.harivansh.tracker.model;


import com.harivansh.tracker.validation.CreateValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is required", groups = {CreateValidationGroup.class})
    @Column(name = "title")
    private String taskTitle;
    @NotBlank(message = "Description is required", groups = {CreateValidationGroup.class})
    @Column(name = "description")
    private String taskDescription;
    @NotNull(message = "Due date is required", groups = {CreateValidationGroup.class})
    @Column(name = "due_date")
    private LocalDate dueDate;

}
