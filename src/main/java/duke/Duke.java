package main.java.duke;

import main.java.duke.task.Deadline;
import main.java.duke.task.Event;
import main.java.duke.task.Task;
import main.java.duke.task.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {

    static String separateLine="_____________________________________________";
    private static List<Task> list = new ArrayList<Task>();

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(separateLine);
        System.out.println("Hello! I am Duke\nWhat can I do for you?");
        System.out.println(separateLine);

        String line = "";
        Scanner in = new Scanner(System.in);
        while (!(line = in.nextLine()).equals("bye")) {
            if (line.equals("list")) {
                System.out.println(separateLine + "\n" + "Here are the tasks in your list:");
                //System.out.println(list.toString());
                //Stream.of(list.toString()).forEach(System.out::println);
                for (int i = 1; i <= list.size(); i++) {
                    //System.out.println(i+". "+"["+list.get(i-1).getStatusIcon()+"] "+list.get(i-1).getDescription());
                    System.out.println(i + ". " + list.get(i - 1).toString());
                }
                System.out.println(separateLine);
            } else if (line.contains("done")) {
                int idx = Integer.parseInt(line.substring(5));
                list.get(idx - 1).markAsDone();
                System.out.println(separateLine + "\n" + "Nice! I've marked this task as done");
                System.out.println("[" + list.get(idx - 1).getStatusIcon() + "] " + list.get(idx - 1).getDescription());
            } else {
                try { addTask(line);}
                catch (EmptyDescriptionException e) {
                    System.out.println(e.getMessage());
                } catch (UndefinedTaskException e) {
                    System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            }
        }
        System.out.println(separateLine + "\nBye. Hope to see you again soon!");
        System.exit(0);
    }
    private static void addTask(String inputString) throws EmptyDescriptionException, UndefinedTaskException {

        try {
            if(inputString.startsWith("todo") || inputString.startsWith("deadline") || inputString.startsWith("event"))
            {
                System.out.println(separateLine + "\n" + "Got it. I've added this task: ");
                String[] a = inputString.split(" ", 2);
                if (a[1].equals("")) {
                    throw new EmptyDescriptionException("You did't enter any description in the task");
                }
                String taskType = a[0];
                if (taskType.equals("todo")) {
                    list.add(new Todo(a[1]));
                } else if (taskType.equals("deadline")) {
                    if (!a[1].contains("/by") || a[1].startsWith("/by")) {
                        throw new EmptyDescriptionException("You didn't enter any description or the deadline time.\n" +
                                " And please enter task followed by time.");
                    }
                    String[] task = a[1].split(" /by ");
                    list.add(new Deadline(task[0], task[1]));
                } else if (taskType.equals("event")) {
                    if (!a[1].contains("/at") || a[1].startsWith("/at")) {
                        throw new EmptyDescriptionException("You didn't enter any description or the event time.\n" +
                                " And please enter task followed by time.");
                    }
                    String[] task = a[1].split(" /at ");
                    list.add(new Event(task[0], task[1]));
                }
                System.out.println(list.get(list.size() - 1).toString());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
            }

            else {
                    throw new UndefinedTaskException();
                }
            }catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println(e.getMessage()+": You entered the task with no description or time");
            }

    }

}
