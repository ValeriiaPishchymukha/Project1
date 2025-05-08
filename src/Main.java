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
        String trim = "";
        while (running) {
            String command = input.nextLine().trim();
            switch (command) {
                case "add":
                    System.out.println("Enter the ID of the task: ");
                    id = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter the title of the task: ");
                    String title = input.nextLine();
                    System.out.println("Enter the description of the task: ");
                    String description = input.nextLine();
                    System.out.println("Enter the deadline of the task (dd.MM.yyyy HH:mm): ");
                    LocalDateTime deadline = LocalDateTime.parse(input.nextLine(), f);
                    System.out.println("Select Priority: 1. LOW  2. MEDIUM  3. HIGH");
                    choice = input.nextInt();
                    priority = switch (choice) {
                        case 1 -> Task.Priority.LOW;
                        case 3 -> Task.Priority.HIGH;
                        default -> Task.Priority.MEDIUM;
                    };
                    System.out.println("Select Status: 1. TODO  2. IN_PROGRESS  3. DONE  4. CANCELLED");
                    choice = input.nextInt();
                    status = switch (choice) {
                        case 2 -> Task.Status.IN_PROGRESS;
                        case 3 -> Task.Status.DONE;
                        case 4 -> Task.Status.CANCELLED;
                        default -> Task.Status.TODO;
                    };

                    tasks.add(new Task(id, title, description, deadline, priority, status));
                    System.out.println("New task is added.");
                    trim = input.nextLine();
                    break;
                case "view all":
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks to view");
                    } else {
                        for (Task task : tasks) {
                            System.out.println(task);
                        }
                    }
                    break;
                case "read":
                    System.out.println("Enter the ID of the task: ");
                    id = input.nextInt();
                    for (Task task : tasks) {
                        if (task.getId()==id) {
                            System.out.println(task);
                        }
                    }
                    trim = input.nextLine();
                    break;
                case "update":
                    System.out.println("Enter the ID of the task: ");
                    id = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter the field you want to change: 1. Title  2. Description  3. Deadline  4. Priority  5. Status");
                    choice = input.nextInt();
                    System.out.print("Enter the new value: ");
                    for (Task task : tasks) {
                        if (task.getId()==id) {
                            switch(choice){
                                case 1: task.setTitle(input.nextLine());
                                    break;
                                case 2: task.setDescription(input.nextLine());
                                    break;
                                case 3:
                                    System.out.print("(dd.MM.yyyy HH:mm) ");
                                    LocalDateTime newDeadline = LocalDateTime.parse(input.nextLine(), f);
                                    task.setDeadline(newDeadline);
                                    break;
                                case 4:
                                    System.out.print("(1. LOW  2. MEDIUM  3. HIGH) ");
                                    int value1 = input.nextInt();
                                    priority = switch (value1) {
                                        case 1 -> Task.Priority.LOW;
                                        case 3 -> Task.Priority.HIGH;
                                        default -> Task.Priority.MEDIUM;
                                    };
                                    task.setPriority(priority);
                                    break;
                                case 5:
                                    System.out.print("(1. TODO  2. IN_PROGRESS  3. DONE  4. CANCELLED) ");
                                    int value2 = input.nextInt();
                                    status = switch (value2) {
                                        case 2 -> Task.Status.IN_PROGRESS;
                                        case 3 -> Task.Status.DONE;
                                        case 4 -> Task.Status.CANCELLED;
                                        default -> Task.Status.TODO;
                                    };
                                    task.setStatus(status);
                                    break;
                                default: break;
                            }
                        }
                    }
                    System.out.println("The task was updated.");
                    trim = input.nextLine();
                    break;
                case "delete":
                    System.out.println("Enter the ID of the task: ");
                    id = input.nextInt();
                    for (Task task : tasks) {
                        if (task.getId()==id) {
                            tasks.remove(task);
                            break;
                        }
                    }
                    System.out.println("The task was deleted.");
                    trim = input.nextLine();
                    break;
                case "find":
                    // TODO find tasks by one of fields
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