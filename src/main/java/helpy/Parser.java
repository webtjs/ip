package helpy;

import helpy.command.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    public Command parse(String fullCommand) {
        if (fullCommand.trim().equals("bye")) {
            return new ExitCommand();
        }
        if (fullCommand.trim().equals("list")) {
            return new ListCommand();
        }
        if (fullCommand.startsWith("todo")) {
            String commandBody = fullCommand.replace("todo", "").trim();
            return new TodoCommand(commandBody);
        }
        if (fullCommand.startsWith("mark")) {
            String commandBody = fullCommand.replace("mark", "").trim();
            return new MarkCommand("mark", commandBody);
        }
        if (fullCommand.startsWith("unmark")) {
            String commandBody = fullCommand.replace("unmark", "").trim();
            return new MarkCommand("unmark", commandBody);
        }
        if (fullCommand.startsWith("event")) {
            String commandBody = fullCommand.replace("event", "").trim();
            return new EventCommand(commandBody);
        }
        if (fullCommand.startsWith("deadline")) {
            String commandBody = fullCommand.replace("deadline", "").trim();
            return new DeadlineCommand(commandBody);
        }
        if (fullCommand.startsWith("delete")) {
            String commandBody = fullCommand.replace("delete", "").trim();
            return new DeleteCommand(commandBody);
        }
        return new UnknownCommand(fullCommand);
    }
}
