package command.impl.reservation;

import command.Command;
import command.CommandResult;
import entity.User;
import entity.room.RoomClass;
import exception.ServiceException;
import service.api.ReservationService;
import service.api.RoomClassService;
import validation.api.BookingDetailsValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookCommand implements Command {

    private static final CommandResult COMMAND_RESULT =
            CommandResult.createRedirectCommandResult("/controller?command=show_reservations_page");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ARRIVAL_DATE_PARAMETER = "arrival_date";
    private static final String DEPARTURE_DATE_PARAMETER = "departure_date";
    private static final String ROOM_CLASS_PARAMETER = "room_class";
    private static final String PERSONS_AMOUNT_PARAMETER = "persons_amount";
    private static final String USER_ATTRIBUTE = "user";

    private RoomClassService roomClassService;
    private ReservationService reservationService;
    private BookingDetailsValidator validator;

    public BookCommand(RoomClassService roomClassService, ReservationService reservationService,
                       BookingDetailsValidator validator) {
        this.roomClassService = roomClassService;
        this.reservationService = reservationService;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String arrivalDateParameter = request.getParameter(ARRIVAL_DATE_PARAMETER);
            Date arrivalDate = DATE_FORMAT.parse(arrivalDateParameter);
            String departureDateParameter = request.getParameter(DEPARTURE_DATE_PARAMETER);
            Date departureDate = DATE_FORMAT.parse(departureDateParameter);
            if (!validator.isPeriodOfStayValid(arrivalDate, departureDate)) {
                throw new ServiceException("Invalid stay period: " + arrivalDate + ", " + departureDate);
            }

            String roomClassName = request.getParameter(ROOM_CLASS_PARAMETER);
            RoomClass roomClass = roomClassService.getByName(roomClassName)
                    .orElseThrow(() -> new ServiceException("Invalid room class: " + roomClassName));

            String personsAmountParameter = request.getParameter(PERSONS_AMOUNT_PARAMETER);
            int personsAmount = Integer.parseInt(personsAmountParameter);
            if (!validator.isPersonsAmountValid(personsAmount)) {
                throw new ServiceException("Invalid amount of persons: " + personsAmount);
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            reservationService.book(user, arrivalDate, departureDate, roomClass, personsAmount);
            return COMMAND_RESULT;
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
