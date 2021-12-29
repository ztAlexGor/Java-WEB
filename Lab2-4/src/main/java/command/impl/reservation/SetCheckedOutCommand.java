package command.impl.reservation;

import command.Command;
import command.CommandResult;
import exception.ServiceException;
import service.api.ReservationService;
import utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetCheckedOutCommand implements Command {

    private static final String ID_PARAMETER = "id";

    private ReservationService reservationService;

    public SetCheckedOutCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        reservationService.setCheckedOut(id);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
