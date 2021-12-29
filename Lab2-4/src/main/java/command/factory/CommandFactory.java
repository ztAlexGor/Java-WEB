package command.factory;

import command.Command;

public interface CommandFactory {
    Command createCommand(String commandName);
}
