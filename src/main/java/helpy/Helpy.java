package helpy;

import helpy.exceptions.IllegalDescriptionException;
import helpy.task.Deadline;
import helpy.task.Event;
import helpy.task.Task;
import helpy.task.Todo;

import java.util.Scanner;
import java.util.ArrayList;

public class Helpy {
    public static ArrayList<Task> taskList = new ArrayList<>();
    public static final String HORIZONTAL_LINE = "______________________\n";

    public static void greetUser() {
        String name = "░▒█░▒█░▒█▀▀▀░▒█░░░░▒█▀▀█░▒█░░▒█\n" +
                "░▒█▀▀█░▒█▀▀▀░▒█░░░░▒█▄▄█░▒▀▄▄▄▀\n" +
                "░▒█░▒█░▒█▄▄▄░▒█▄▄█░▒█░░░░░░▒█░░\n";
        System.out.println(HORIZONTAL_LINE
                + "Greetings, I am\n" + name);
        System.out.println("How can I help you?\n" + HORIZONTAL_LINE);
    }

    public static void listTasks(ArrayList<Task> taskList) {
        int label = 1;

        System.out.print(HORIZONTAL_LINE);
        System.out.println("These are the tasks in your list:");
        for (Task task : taskList) {
            System.out.print(label + ".");
            System.out.println(task);
            label++;
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public static void printMessage(String message) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(HORIZONTAL_LINE);
    }

    public static void markTask(Task task, String commandStartsWith) {

        System.out.print(HORIZONTAL_LINE);

        if (commandStartsWith.equals("mark")) {
            task.setDone(true);
            System.out.println("Good job! I've marked this task as done:");
        } else {
            task.setDone(false);
            System.out.println("Ok, this task has been marked as not done yet:");
        }

        System.out.println("\t" + task);
        System.out.println(HORIZONTAL_LINE);
    }

    public static void deleteTask(String command) {
        String taskNum = command.replace("delete", "");
        taskNum = taskNum.trim();
        int taskIndex = Integer.parseInt(taskNum) - 1;
        Task removedTask = taskList.remove(taskIndex);
        int numOfTasks = taskList.toArray().length;

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it, I've removed this task from the list:\n" +
                "\t" + removedTask);
        switch (numOfTasks) {
        case 0:
            System.out.println("There are no more tasks left in the list.");
            break;
        case 1:
            System.out.println("There is 1 task left in the list.");
            break;
        default:
            System.out.println("There are " + numOfTasks + " tasks left in the list.");
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public static void addMessage(ArrayList<Task> taskList) {
        int numOfTasks = taskList.toArray().length;
        Task theTask = taskList.get(numOfTasks - 1);
        System.out.print(HORIZONTAL_LINE);
        System.out.println("Ok I just added: ");
        System.out.println("\t" + theTask);

        if (numOfTasks == 1) {
            System.out.println("There is 1 task in the list.");
        } else {
            System.out.println("There are " + numOfTasks
                    + " tasks in the list.");
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public static void processCommand(String command, ArrayList<Task> taskList) {

        if (command.startsWith("mark") || command.startsWith("unmark")) {
            String taskNum;
            String commandStartsWith;
            if (command.startsWith("mark")) {
                taskNum = command.replace("mark", "");
                commandStartsWith = "mark";
            } else {
                taskNum = command.replace("unmark", "");
                commandStartsWith = "unmark";
            }
            taskNum = taskNum.trim();
            try {
                int taskIndex = Integer.parseInt(taskNum) - 1;
                markTask(taskList.get(taskIndex), commandStartsWith);
            } catch (NumberFormatException e) {
                printMessage("Task number provided is invalid! " +
                        "Did you enter wrongly? You typed: " + command);
            } catch (IndexOutOfBoundsException e) {
                printMessage("Task number doesn't exist! " +
                        "Did you enter wrongly? You typed: " + command);
            }
            return;
        }

        if (command.startsWith("todo")) {
            try {
                Todo newTodo = new Todo(command);
                taskList.add(newTodo);
            } catch (IllegalDescriptionException e) {
                printMessage("Your todo description is empty!");
                return;
            }
        } else if (command.startsWith("deadline")) {
            try {
                Deadline newDeadline = new Deadline(command);
                taskList.add(newDeadline);
            } catch (ArrayIndexOutOfBoundsException e) {
                printMessage("Invalid format for deadline! Make sure it follows: " +
                        "deadline <description> /by <date>");
                return;
            }
        } else if (command.startsWith("event")) {
            try {
                Event newEvent = new Event(command);
                taskList.add(newEvent);
            } catch (ArrayIndexOutOfBoundsException e) {
                printMessage("Invalid format for event! Make sure it's in this format: " +
                        "event <description> /from <start date> /to <end date>");
                return;
            }
        } else if (command.startsWith("delete")) {
            try {
                deleteTask(command);
            } catch (NumberFormatException e) {
                printMessage("Task number provided is invalid! " +
                        "Did you enter wrongly? You typed: " + command);
            } catch (IndexOutOfBoundsException e) {
                printMessage("Task number doesn't exist! " +
                        "Did you enter wrongly? You typed: " + command);
            }
            return;
        } else {
            printMessage("I don't understand the command \"" + command
                    + "\". Can you check that you typed correctly?");
            return;
        }
        addMessage(taskList);
    }

    public static void main(String[] args) {
        greetUser();

        Scanner in = new Scanner(System.in);
        String command = "";

        while (!command.equals("bye")) {
            command = in.nextLine();
            switch (command) {
            case "bye":
                printMessage("Goodbye, see you next time!");
                break;
            case "list":
                listTasks(taskList);
                break;
            default:
                processCommand(command, taskList);
                break;
            }
        }
    }
}
