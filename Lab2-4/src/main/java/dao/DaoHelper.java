package dao;

import builder.Builder;
import dao.api.ReservationDao;
import dao.api.RoomClassDao;
import dao.api.RoomDao;
import dao.api.UserDao;
import dao.impl.ReservationDaoImpl;
import dao.impl.RoomClassDaoImpl;
import dao.impl.RoomDaoImpl;
import dao.impl.UserDaoImpl;
import entity.User;
import entity.reservation.Reservation;
import entity.room.Room;
import entity.room.RoomClass;
import exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoHelper {

    private Connection connection;

    public DaoHelper(Connection connection) {
        this.connection = connection;
    }

    public UserDao userDao(Builder<User> builder) {
        return new UserDaoImpl(builder, connection);
    }

    public RoomClassDao roomClassDao(Builder<RoomClass> builder) {
        return new RoomClassDaoImpl(builder, connection);
    }

    public RoomDao roomDao(Builder<Room> builder) {
        return new RoomDaoImpl(builder, connection);
    }

    public ReservationDao reservationDao(Builder<Reservation> builder) {
        return new ReservationDaoImpl(builder, connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void cancelTransaction() throws DaoException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

}
