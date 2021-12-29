package command.impl;

import command.Command;
import command.CommandResult;
import entity.room.RoomClass;
import exception.ServiceException;
import service.api.RoomClassService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowBookPageCommand implements Command {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final CommandResult COMMAND_RESULT = CommandResult.createForwardCommandResult("/book");

    private RoomClassService roomClassService;

    public ShowBookPageCommand(RoomClassService roomClassService) {
        this.roomClassService = roomClassService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<RoomClass> roomClasses = roomClassService.getAllRoomClasses();
        request.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
        return COMMAND_RESULT;
    }

}
