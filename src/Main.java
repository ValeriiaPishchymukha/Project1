import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        Scanner input = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        System.out.println("Possible commands: add, view all, read, update, find, sort, delete, exit.");
        System.out.println("Enter command: ");
        boolean running = true;
        int id, choice;
        Task.Priority priority;
        Task.Status status;
        while (running) {
            switch (input.nextLine()) {
                case "add":
                    // TODO add a new task
                    break;
                case "view all":
                    // TODO list all the tasks
                    break;
                case "read":
                    // TODO show task by ID
                    break;
                case "update":
                    // TODO update field of the task found by ID
                    break;
                case "find":
                    // TODO find tasks by one of fields
                    break;
                case "delete":
                    // TODO delete task by ID
                    break;
                case "sort":
                    // TODO sort tasks by one of fields
                    break;
                case "exit":
                    System.out.println("Application closed");
                    running = false;
                    break;
                default:
                    System.out.println("Command not recognized!");
                    break;
            }
        }
        input.close();
    }
}