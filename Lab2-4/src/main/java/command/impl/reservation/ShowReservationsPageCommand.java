package command.impl.reservation;

import utils.ReservationPriceCalculator;
import utils.RoomUtils;
import command.Command;
import command.CommandResult;
import entity.User;
import entity.reservation.Reservation;
import entity.reservation.ReservationStatus;
import entity.room.Room;
import exception.ServiceException;
import service.api.ReservationService;
import service.api.RoomService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class ShowReservationsPageCommand extends AbstractReservationCommand implements Command {

    private static final String DETAILS_ID_PARAMETER = "id";
    private static final String RESERVATIONS_ATTRIBUTE = "reservations";
    private static final String RESERVATION_DETAILS_ATTRIBUTE = "reservation_details";
    private static final String SUITABLE_ROOMS_ATTRIBUTE = "rooms";
    private static final CommandResult COMMAND_RESULT = CommandResult.createForwardCommandResult("/reservations");

    private ReservationService reservationService;
    private RoomService roomService;
    private RoomUtils roomUtils;

    public ShowReservationsPageCommand(ReservationService reservationService, RoomService roomService,
                                       RoomUtils roomUtils) {
        super(reservationService);
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.roomUtils = roomUtils;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = getUser(request);
        loadReservations(request, user);

        if (shouldShowDetails(request)) {
            checkCredentials(request);
            Reservation reservation = getReservation(request);
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (user.isAdmin() && reservationStatus == ReservationStatus.WAITING) {
                processWaitingReservation(request, reservation);
            }
            request.setAttribute(RESERVATION_DETAILS_ATTRIBUTE, reservation);
        }

        return COMMAND_RESULT;
    }

    private void loadReservations(HttpServletRequest request, User user) throws ServiceException {
        List<Reservation> reservations;
        if (user.isAdmin()) {
            reservations = reservationService.getAllReservations();
        } else {
            int id = user.getId();
            reservations = reservationService.getByUserId(id);
        }
        request.setAttribute(RESERVATIONS_ATTRIBUTE, reservations);
    }

    private boolean shouldShowDetails(HttpServletRequest request) {
        String detailsId = request.getParameter(DETAILS_ID_PARAMETER);
        return detailsId != null;
    }

    private void processWaitingReservation(HttpServletRequest request, Reservation reservation) throws ServiceException {
        BigDecimal totalPrice = ReservationPriceCalculator.calculateReservationPrice(reservation);
        reservation.setTotalPrice(totalPrice);

        List<Room> rooms = roomService.getAllRooms();
        List<Reservation> reservations = reservationService.getAllReservations();
        List<Room> suitableRooms = roomUtils.getAvailableRooms(rooms, reservations, reservation);
        request.setAttribute(SUITABLE_ROOMS_ATTRIBUTE, suitableRooms);
    }

}
