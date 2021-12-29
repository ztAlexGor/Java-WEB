package command.impl.room;

import command.Command;
import command.CommandResult;
import utils.CurrentPageGetter;
import exception.ServiceException;
import service.api.RoomService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeRoomStatusCommand implements Command {

    private static final String CHECKBOX_NAME = "checkbox";
    private static final String ID_PARAMETER = "id";

    private RoomService roomService;

    public ChangeRoomStatusCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        String checkboxParameter = request.getParameter(CHECKBOX_NAME);
        boolean active = isCheckboxChecked(checkboxParameter);

        roomService.setActiveById(id, active);

        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

    private boolean isCheckboxChecked(String checkboxParameter) {
        return checkboxParameter != null;
    }

}
