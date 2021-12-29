package command.impl.reservation;

import command.Command;
import command.CommandResult;
import entity.reservation.Reservation;
import entity.room.Room;
import exception.ServiceException;
import service.api.ReservationService;
import service.api.RoomService;
import utils.CurrentPageGetter;
import utils.ReservationPriceCalculator;
import utils.RoomUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ApproveCommand extends AbstractReservationCommand implements Command {

    private static final String ROOM_ID_PARAMETER = "room_id";

    private RoomService roomService;
    private ReservationService reservationService;
    private RoomUtils roomUtils;

    public ApproveCommand(RoomService roomService, ReservationService reservationService, RoomUtils roomUtils) {
        super(reservationService);
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.roomUtils = roomUtils;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Reservation reservation = getReservation(request);
        int reservationId = reservation.getId();

        String roomIdParameter = request.getParameter(ROOM_ID_PARAMETER);
        int roomId = Integer.parseInt(roomIdParameter);
        Room room = roomService.getById(roomId)
                .orElseThrow(() -> new ServiceException("Room not found by id: " + roomId));
        validateRoom(room, reservation);
        BigDecimal totalPrice = ReservationPriceCalculator.calculateReservationPrice(reservation);

        reservationService.approve(reservationId, room, totalPrice);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

    private void validateRoom(Room room, Reservation reservation) throws ServiceException {
        if (!room.isActive()) {
            throw new ServiceException("Room is not active");
        }
        if (!roomUtils.isRoomSuitable(room, reservation)) {
            throw new ServiceException("Room is not suitable");
        }
        List<Reservation> reservations = reservationService.getAllReservations();
        Date arrivalDate = reservation.getArrivalDate();
        Date departureDate = reservation.getDepartureDate();
        if (!roomUtils.isRoomFree(room, arrivalDate, departureDate, reservations)) {
            throw new ServiceException("Room is not free on the specified date");
        }
    }

}
