package command.impl;

import command.Command;
import command.CommandResult;
import exception.ServiceException;
import utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
