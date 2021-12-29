package command.impl.reservation;

import command.Command;
import command.CommandResult;
import entity.reservation.Reservation;
import exception.ServiceException;
import service.api.ReservationService;
import utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelReservationCommand extends AbstractReservationCommand implements Command {

    private ReservationService reservationService;

    public CancelReservationCommand(ReservationService reservationService) {
        super(reservationService);
        this.reservationService = reservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        reservationService.cancel(id);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
