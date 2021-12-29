package service.api;

import entity.room.RoomClass;
import exception.ServiceException;
import java.util.List;
import java.util.Optional;

public interface RoomClassService {

    List<RoomClass> getAllRoomClasses() throws ServiceException;
    Optional<RoomClass> getByName(String name) throws ServiceException;
    void updatePrices(List<RoomClass> roomClasses) throws ServiceException;

}
