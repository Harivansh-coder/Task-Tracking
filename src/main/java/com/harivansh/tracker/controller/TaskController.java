package com.harivansh.tracker.controller;

import com.harivansh.tracker.exception.ResourceNotFoundException;
import com.harivansh.tracker.model.Task;
import com.harivansh.tracker.repository.TaskRepository;
import com.harivansh.tracker.validation.CreateValidationGroup;
import com.harivansh.tracker.validation.UpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

//    create task controller
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Task> createTask(@Validated(CreateValidationGroup.class) @RequestBody Task task) {
        Task createdTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

//    field validation for create controller
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

//    get a task by id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));
        return ResponseEntity.ok(task);
    }

//    get all task from the database
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();
        return ResponseEntity.ok(allTasks);
    }


//    update controller
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable long id, @Validated(UpdateValidationGroup.class) @RequestBody Task updatedTask) {
        try {
            Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));


            if (updatedTask.getTaskTitle() != null) {
                existingTask.setTaskTitle(updatedTask.getTaskTitle());
            }
            if (updatedTask.getTaskDescription() != null) {
                existingTask.setTaskDescription(updatedTask.getTaskDescription());
            }
            if (updatedTask.getDueDate() != null) {
                existingTask.setDueDate(updatedTask.getDueDate());
            }

            // Save the updated task
            Task savedTask = taskRepository.save(existingTask);

            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            // Handle the exception and provide a custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with the specified ID does not exist.");
        } catch (Exception e) {
            // Handle other exceptions, if necessary
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    delete controller
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));
            taskRepository.delete(task);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            // Handle the exception and provide a custom error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with the specified ID does not exist.");
        } catch (Exception e) {
            // Handle other exceptions, if necessary
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
