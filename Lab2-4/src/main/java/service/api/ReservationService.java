package service.api;

import entity.User;
import entity.reservation.Reservation;
import entity.room.Room;
import entity.room.RoomClass;
import exception.ServiceException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<Reservation> getAllReservations() throws ServiceException;

    Optional<Reservation> getById(int id) throws ServiceException;

    List<Reservation> getByUserId(int userId) throws ServiceException;

    void book(User user, Date arrivalDate, Date departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException;

    void approve(int id, Room room, BigDecimal totalPrice) throws ServiceException;

    void setPaid(int id) throws ServiceException;

    void setCheckedIn(int id) throws ServiceException;

    void setCheckedOut(int id) throws ServiceException;

    void cancel(int id) throws ServiceException;

}
