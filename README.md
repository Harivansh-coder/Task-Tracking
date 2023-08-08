# Task-Tracking

## Tech Stack
* Java
* Spring Boot
* PostgreSQL

## Run the App
Follow these steps to run the app.
1. Clone the repository.
   
```
 git clone https://github.com/Harivansh-coder/Task-Tracking.git
``` 
3. Open the project in an IDLE like Intellij or something, set up the dependency, and build tool(gradle in this case).
4. PostgreSQL needs to be installed on your system and you can modify its configs in the application.properties file.
5. Run the TrackerApplication file.

## API Routes
```
POST /tasks: Create a new task.
GET /tasks/{id}: Retrieve a task by its ID.
GET /tasks: Retrieve a list of all tasks.
PUT /tasks/{id}: Update an existing task by its ID.
DELETE /tasks/{id}: Delete a task by its ID.
```
