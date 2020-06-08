package todo;

import todo.manager.ToDoManager;
import todo.manager.UserManager;
import todo.model.ToDo;
import todo.model.ToDoStatus;
import todo.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ToDoMain implements Commands {

    private static Scanner scanner = new Scanner(System.in);

    private static UserManager userManager = new UserManager();
    private static ToDoManager toDoManager = new ToDoManager();

    private static User currentUser;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    private static void register() {
        System.out.println("Please input data for registration");
        User user = new User();
        System.out.print("Name: ");
        user.setName(scanner.nextLine());
        System.out.print("Surname: ");
        user.setSurname(scanner.nextLine());
        System.out.print("Email: ");
        user.setEmail(scanner.nextLine());
        System.out.print("Password: ");
        user.setPassword(scanner.nextLine());
        User userbyEmail = userManager.getByEmail(user.getEmail());
        if (userbyEmail == null) {
            userManager.register(user);
            System.out.println("User was successfully added!");
        } else {
            System.out.println("User with email" + user.getEmail() + " already exists");
            register();
        }
    }

    private static void login() {
        System.out.println("Please input email & password");
        System.out.print("Email: ");
        String userEmail = scanner.nextLine();
        System.out.print("Password: ");
        String userPassword = scanner.nextLine();
        User user = userManager.getByEmailAndPassword(userEmail, userPassword);
        if (user != null) {
            currentUser = user;
            loginSuccess();
        } else {
            System.out.println("Wrong email or password");
        }
    }

    private static void loginSuccess() {
        System.out.println("Welcome to your account " + currentUser.getName() + " !");
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_TODO:
                    addNewToDo();
                    break;
                case MY_ALL_LIST:
                    printToDos(toDoManager.getAllToDosByUser(currentUser.getId()));
                    break;
                case MY_TODO_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.TODO));
                    break;
                case MY_IN_PROGRESS_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.TODO));
                    break;
                case MY_FINISHED_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.FINISHED));
                    break;
                case CHANGE_TODO_STATUS:
                    changeToDoStatus();
                    break;
                case DELETE_TODO:
                    deleteToDo();
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }

    }

    private static void addNewToDo() {
        System.out.println("Please input data to add new ToDo");
        System.out.print("Title: ");
        String toDoTitle = scanner.nextLine();
        System.out.print("Deadline (yyyy-MM-dd HH:mm:ss): ");
        String toDoDeadline = scanner.nextLine();
        ToDo toDo = new ToDo();
        toDo.setTitle(toDoTitle);
        try {
            if (toDoDeadline != null) {
                toDo.setDeadline(sdf.parse(toDoDeadline));
            }
        } catch (ParseException e) {
            System.out.println("");
        }
        toDo.setStatus(ToDoStatus.TODO);
        toDo.setUser(currentUser);
        if (toDoManager.create(toDo)) {
            System.out.println("Todo was added");
        } else {
            System.out.println("Something went wrong");
        }
    }

    private static void printToDos(List<ToDo> allToDosByUser) {
        for (ToDo toDo : allToDosByUser) {
            System.out.println(toDo);
        }
    }


    private static void changeToDoStatus() {
        System.out.println("Please choose Todo from list:");
        List<ToDo> allToDosByUser = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : allToDosByUser) {
            System.out.println(toDo);
        }

        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("Please choose status: " + Arrays.toString(ToDoStatus.values()));
            ToDoStatus status = ToDoStatus.valueOf(scanner.nextLine().toUpperCase());
            if (toDoManager.update(id, status)) {
                System.out.println(toDoManager.getById(id) + " status was changed");
            } else {
                System.out.println("Something went wrong!");
            }

        } else {
            System.out.println("Wrong ID!");
        }
    }

    private static void deleteToDo() {
        System.out.println("Please choose Todo from list:");
        List<ToDo> allToDosByUser = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : allToDosByUser) {
            System.out.println(toDo);
        }

        long id = Long.parseLong(scanner.nextLine());

        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            toDoManager.delete(id);
        } else {
            System.out.println("Wrong ID");
        }
    }


}
