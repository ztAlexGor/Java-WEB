package dao.api;

import entity.reservation.Reservation;
import exception.DaoException;
import java.util.List;

public interface ReservationDao extends Dao<Reservation> {
    List<Reservation> getByUserId(int id) throws DaoException;
}
