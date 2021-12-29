package command.factory;

import builder.Builder;
import builder.impl.ReservationBuilder;
import builder.impl.RoomBuilder;
import builder.impl.RoomClassBuilder;
import builder.impl.UserBuilder;
import command.impl.*;
import command.impl.room.ChangeRoomStatusCommand;
import command.impl.room.SavePricesCommand;
import command.impl.reservation.ApproveCommand;
import command.impl.reservation.BookCommand;
import command.impl.reservation.CancelReservationCommand;
import command.impl.reservation.PayCommand;
import command.impl.reservation.SetCheckedInCommand;
import command.impl.reservation.SetCheckedOutCommand;
import command.impl.reservation.ShowReservationsPageCommand;
import command.impl.room.ShowRoomsPageCommand;
import service.api.ReservationService;
import service.api.RoomClassService;
import service.api.RoomService;
import service.api.UserService;
import command.Command;
import service.impl.ReservationServiceImpl;
import service.impl.RoomClassServiceImpl;
import service.impl.RoomServiceImpl;
import service.impl.UserServiceImpl;
import utils.DateUtils;
import utils.RoomUtils;
import dao.DaoHelper;
import entity.User;
import entity.reservation.Reservation;
import entity.room.Room;
import entity.room.RoomClass;
import validation.impl.BookingDetailsValidatorImpl;
import validation.impl.PriceValidatorImpl;

public class CommandFactoryImpl implements CommandFactory {

    // admin commands
    public static final String SAVE_PRICES_COMMAND = "save_prices";
    public static final String CHANGE_ROOM_STATUS_COMMAND = "change_room_status";
    public static final String APPROVE_COMMAND = "approve";
    public static final String CHECK_IN_COMMAND = "check_in";
    public static final String CHECK_OUT_COMMAND = "check_out";
    public static final String SHOW_ROOMS_PAGE_COMMAND = "show_rooms_page";

    // user commands
    public static final String BOOK_COMMAND = "book";
    public static final String PAY_COMMAND = "pay";
    public static final String CANCEL_RESERVATION_COMMAND = "cancel_reservation";
    public static final String SIGN_OUT_COMMAND = "sign_out";
    public static final String SHOW_BOOK_PAGE_COMMAND = "show_book_page";
    public static final String SHOW_RESERVATIONS_PAGE_COMMAND = "show_reservations_page";

    // any credentials commands
    public static final String SHOW_HOME_PAGE_COMMAND = "show_home_page";
    public static final String SHOW_LOGIN_PAGE_COMMAND = "show_login_page";
    public static final String LOGIN_COMMAND = "login";
    public static final String UPDATE_PAGE_COMMAND = "update_page";

    // URLs
    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_PAGE_URL = "/login";

    private DaoHelper daoHelper;

    public CommandFactoryImpl(DaoHelper daoHelper) {
        this.daoHelper = daoHelper;
    }

    @Override
    public Command createCommand(String commandName) {
        Command command;
        switch (commandName) {
            case SHOW_HOME_PAGE_COMMAND:
                command = new ShowPageCommand(HOME_PAGE_URL);
                break;
            case SHOW_LOGIN_PAGE_COMMAND:
                command = new ShowPageCommand(LOGIN_PAGE_URL);
                break;
            case LOGIN_COMMAND:
                command = new LoginCommand(
                        getUserService()
                );
                break;
            case SIGN_OUT_COMMAND:
                command = new SignOutCommand();
                break;
            case BOOK_COMMAND:
                command = new BookCommand(
                        getRoomClassService(),
                        getReservationService(),
                        new BookingDetailsValidatorImpl(new DateUtils())
                );
                break;
            case SHOW_BOOK_PAGE_COMMAND:
                command = new ShowBookPageCommand(
                        getRoomClassService()
                );
                break;
            case SHOW_ROOMS_PAGE_COMMAND:
                command = new ShowRoomsPageCommand(
                        getRoomClassService(),
                        getRoomService()
                );
                break;
            case SAVE_PRICES_COMMAND:
                command = new SavePricesCommand(
                        getRoomClassService(),
                        new PriceValidatorImpl()
                );
                break;
            case CHANGE_ROOM_STATUS_COMMAND:
                command = new ChangeRoomStatusCommand(
                      getRoomService()
                );
                break;
            case SHOW_RESERVATIONS_PAGE_COMMAND:
                command = new ShowReservationsPageCommand(
                        getReservationService(),
                        getRoomService(),
                        new RoomUtils(new DateUtils())
                );
                break;
            case CANCEL_RESERVATION_COMMAND:
                command = new CancelReservationCommand(
                        getReservationService()
                );
                break;
            case APPROVE_COMMAND:
                command = new ApproveCommand(
                        getRoomService(),
                        getReservationService(),
                        new RoomUtils(new DateUtils())
                );
                break;
            case PAY_COMMAND:
                command = new PayCommand(
                        getReservationService()
                );
                break;
            case CHECK_IN_COMMAND:
                command = new SetCheckedInCommand(
                        getReservationService()
                );
                break;
            case CHECK_OUT_COMMAND:
                command = new SetCheckedOutCommand(
                      getReservationService()
                );
                break;
            case UPDATE_PAGE_COMMAND:
                command = new UpdatePageCommand();
                break;
            default:
                throw new IllegalArgumentException("Invalid command: " + commandName);
        }
        return command;
    }

    private UserService getUserService() {
        Builder<User> builder = new UserBuilder();
        return new UserServiceImpl(daoHelper, builder);
    }

    private RoomService getRoomService() {
        Builder<Room> builder = new RoomBuilder();
        return new RoomServiceImpl(daoHelper, builder);
    }

    private RoomClassService getRoomClassService() {
        Builder<RoomClass> builder = new RoomClassBuilder();
        return new RoomClassServiceImpl(daoHelper, builder);
    }

    private ReservationService getReservationService() {
        Builder<Reservation> builder = new ReservationBuilder();
        return new ReservationServiceImpl(daoHelper, builder);
    }

}
