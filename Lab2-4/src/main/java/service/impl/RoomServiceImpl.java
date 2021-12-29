package service.impl;

import builder.Builder;
import dao.DaoHelper;
import dao.api.RoomDao;
import entity.room.Room;
import exception.DaoException;
import exception.ServiceException;
import service.api.RoomService;
import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {

    private RoomDao dao;

    public RoomServiceImpl(DaoHelper daoHelper, Builder<Room> builder) {
        this.dao = daoHelper.roomDao(builder);
    }

    @Override
    public Optional<Room> getById(int id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Room> getAllRooms() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setActiveById(int id, boolean active) throws ServiceException {
        try {
            Room room = dao.getById(id)
                    .orElseThrow(() -> new ServiceException("Room not found by id=" + id));
            room.setActive(active);
            dao.save(room);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
