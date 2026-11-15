
package com.example.student_task_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentTaskApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateTask() {
        TaskController controller = new TaskController();

        TaskController.TaskRequest request =
                new TaskController.TaskRequest("Study Java", "Practice programs");

        TaskController.Task task = controller.addTask(request);

        assertEquals("Study Java", task.title());
        assertEquals("PENDING", task.status());
    }

    @Test
    void testGetTask() {
        TaskController controller = new TaskController();

        TaskController.TaskRequest request =
                new TaskController.TaskRequest("Study DBMS", "Practice SQL");

        TaskController.Task created = controller.addTask(request);
        TaskController.Task retrieved = controller.getTask(created.id());

        assertEquals(created.id(), retrieved.id());
        assertEquals("Study DBMS", retrieved.title());
    }
}
