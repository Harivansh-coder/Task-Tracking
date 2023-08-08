package com.harivansh.tracker.controller;

import com.harivansh.tracker.exception.ResourceNotFoundException;
import com.harivansh.tracker.model.Task;
import com.harivansh.tracker.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController()
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //    create task route
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {

        try {
//          save the task to DB
            Task createdTask = taskRepository.save(task);

//            Return Response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }


    //    get a task route
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task does not exits"));
        return ResponseEntity.ok(task);
    }

    //    getAllTask Route
    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
//      getting all text from the db
        List<Task> allTask = taskRepository.findAll();

//        return the list of task
        return ResponseEntity.ok(allTask);
    }

//    update task route

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));

        // Update the values of the existing task using setter methods
        existingTask.setTaskTitle(updatedTask.getTaskTitle());
        existingTask.setTaskDescription(updatedTask.getTaskDescription());
        existingTask.setDueDate(updatedTask.getDueDate());

        // Save the updated task back to the database
        Task savedTask = taskRepository.save(existingTask);

        return ResponseEntity.ok(savedTask);
    }

//    delete task route

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task does not exits"));
        taskRepository.delete(task);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
