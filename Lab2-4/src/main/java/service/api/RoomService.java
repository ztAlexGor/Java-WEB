package service.api;

import entity.room.Room;
import exception.ServiceException;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> getAllRooms() throws ServiceException;
    void setActiveById(int id, boolean active) throws ServiceException;
    Optional<Room> getById(int id) throws ServiceException;

}
