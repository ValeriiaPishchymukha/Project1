import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        Scanner input = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        File file = new File("tasks.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader("tasks.json")) {
                Type tasksType = new TypeToken<List<Task>>(){}.getType();
                tasks = gson.fromJson(reader, tasksType);
                System.out.println("Loaded tasks from file.");
            } catch (IOException e) {
                System.out.println("Error reading tasks: " + e.getMessage());
            }
        }



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

                    LocalDateTime deadline = null;
                    while (deadline == null) {
                        System.out.println("Enter the deadline of the task (dd.MM.yyyy HH:mm): ");
                        String deadlineInput = input.nextLine();
                        try {
                            deadline = LocalDateTime.parse(deadlineInput, f);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

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
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks yet.");
                    } else {
                        System.out.println("Enter the ID of the task: ");
                        id = input.nextInt();
                        for (Task task : tasks) {
                            if (task.getId() == id) {
                                System.out.println(task);
                            }
                        }
                        trim = input.nextLine();
                    }
                    break;
                case "update":
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks yet.");
                    } else {
                        System.out.println("Enter the ID of the task: ");
                        id = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter the field you want to change: 1. Title  2. Description  3. Deadline  4. Priority  5. Status");
                        choice = input.nextInt();
                        System.out.print("Enter the new value: ");
                        for (Task task : tasks) {
                            if (task.getId() == id) {
                                switch (choice) {
                                    case 1:
                                        task.setTitle(input.nextLine());
                                        break;
                                    case 2:
                                        task.setDescription(input.nextLine());
                                        break;
                                    case 3:
                                        System.out.print("(dd.MM.yyyy HH:mm) ");
                                        deadline = null;
                                        while (deadline == null) {
                                            String deadlineInput = input.nextLine();
                                            try {
                                                deadline = LocalDateTime.parse(deadlineInput, f);
                                            } catch (DateTimeParseException e) {
                                                System.out.println("Invalid date format. Please try again.");
                                            }
                                        }
                                        task.setDeadline(deadline);
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
                                    default:
                                        break;
                                }
                                System.out.println("The task was updated.");
                            }
                        }
                        trim = input.nextLine();
                    }
                    break;
                case "delete":
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks yet.");
                    } else {
                        System.out.println("Enter the ID of the task: ");
                        id = input.nextInt();
                        for (Task task : tasks) {
                            if (task.getId() == id) {
                                tasks.remove(task);
                                System.out.println("The task was deleted.");
                                break;
                            }
                        }
                        trim = input.nextLine();
                    }
                    break;
                case "find":
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks yet.");
                    } else {
                        System.out.println("Enter the field you want to search by: 0. ID  1. Title  2. Description  3. Deadline  4. Priority  5. Status");
                        choice = input.nextInt();
                        trim = input.nextLine();
                        int number;
                        String line;
                        int count = 0;
                        System.out.print("Enter the value: ");
                        switch (choice) {
                            case 0:
                                number = input.nextInt();
                                for (Task task : tasks) {
                                    if (task.getId() == number) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                trim = input.nextLine();
                                break;
                            case 1:
                                line = input.nextLine();
                                for (Task task : tasks) {
                                    if (Objects.equals(task.getTitle(), line)) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                break;
                            case 2:
                                line = input.nextLine();
                                for (Task task : tasks) {
                                    if (task.getDescription().contains(line)) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                break;
                            case 3:
                                deadline = null;
                                while (deadline == null) {
                                    System.out.println("Enter the deadline you want to find (dd.MM.yyyy HH:mm): ");
                                    String deadlineInput = input.nextLine();
                                    try {
                                        deadline = LocalDateTime.parse(deadlineInput, f);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid date format. Please try again.");
                                    }
                                }
                                for (Task task : tasks) {
                                    if (Objects.equals(task.getDeadline(), deadline)) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }

                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                break;
                            case 4:
                                System.out.print("(1. LOW  2. MEDIUM  3. HIGH) ");
                                number = input.nextInt();
                                for (Task task : tasks) {
                                    if (task.getPriority().ordinal() + 1 == number) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                trim = input.nextLine();
                                break;
                            case 5:
                                System.out.print("(1. TODO  2. IN_PROGRESS  3. DONE  4. CANCELLED) ");
                                number = input.nextInt();
                                for (Task task : tasks) {
                                    if (task.getStatus().ordinal() + 1 == number) {
                                        System.out.println(task);
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    System.out.println("No results found.");
                                } else {
                                    count = 0;
                                }
                                trim = input.nextLine();
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;

                case "sort":
                    if (tasks.isEmpty()) {
                        System.out.println("There are no tasks to sort");
                    } else {
                        System.out.println("Sort by: 0. ID  1. Title  2. Deadline  3. Priority  4. Status");
                        choice = input.nextInt();
                        switch(choice) {
                            case 1:
                                Collections.sort(tasks, (t1, t2) -> t1.getTitle().compareTo(t2.getTitle()));
                                break;
                            case 2:
                                Collections.sort(tasks, (t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()));
                                break;
                            case 3:
                                Collections.sort(tasks, (t1, t2) -> t2.getPriority().compareTo(t1.getPriority()));
                                break;
                            case 4:
                                Collections.sort(tasks, (t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
                                break;
                            default: Collections.sort(tasks, (t1, t2) -> Integer.compare(t1.getId(), t2.getId()));
                        }
                    }
                    trim = input.nextLine();
                    break;
                case "exit":
                    System.out.println("Application closed");
                    String json = gson.toJson(tasks);
                    try (FileWriter writer = new FileWriter("tasks.json")) {
                        writer.write(json);
                        System.out.println("Tasks saved to tasks.json");
                    } catch (IOException e) {
                        System.out.println("Error saving tasks: " + e.getMessage());
                    }

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