package dao.api;

import entity.room.RoomClass;
import exception.DaoException;
import java.util.Optional;

public interface RoomClassDao extends Dao<RoomClass> {

    Optional<RoomClass> getByName(String name) throws DaoException;

}
