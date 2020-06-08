package todo;

public interface Commands {

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_NEW_TODO = 1;
    int MY_ALL_LIST = 2;
    int MY_TODO_LIST = 3;
    int MY_IN_PROGRESS_LIST = 4;
    int MY_FINISHED_LIST = 5;
    int CHANGE_TODO_STATUS = 6;
    int DELETE_TODO = 7;

    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " to Exit");
        System.out.println("Please input " + LOGIN + " to Login");
        System.out.println("Please input " + REGISTER + " for Registration");
    }

    static void printUserCommands() {
        System.out.println("Please input " + LOGOUT + " to Logout");
        System.out.println("Please input " + ADD_NEW_TODO + " to add new Todo");
        System.out.println("Please input " + MY_ALL_LIST + " to view your all todo's list");
        System.out.println("Please input " + MY_TODO_LIST + " to view your todo list");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " to view your todo's list which are in progress");
        System.out.println("Please input " + MY_FINISHED_LIST + " to view your finished todo's list");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " to change your todo status");
        System.out.println("Please input " + DELETE_TODO + " to delete your todo");
    }


}
