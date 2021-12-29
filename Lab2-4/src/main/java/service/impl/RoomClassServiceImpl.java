package service.impl;

import builder.Builder;
import dao.DaoHelper;
import dao.api.RoomClassDao;
import entity.room.RoomClass;
import exception.DaoException;
import exception.ServiceException;
import service.api.RoomClassService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class RoomClassServiceImpl implements RoomClassService {

    private DaoHelper daoHelper;
    private RoomClassDao dao;

    public RoomClassServiceImpl(DaoHelper daoHelper, Builder<RoomClass> builder) {
        this.daoHelper = daoHelper;
        dao = daoHelper.roomClassDao(builder);
    }

    @Override
    public List<RoomClass> getAllRoomClasses() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<RoomClass> getByName(String name) throws ServiceException {
        try {
            return dao.getByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePrices(List<RoomClass> roomClasses) throws ServiceException {
        try {
            daoHelper.startTransaction();

            for (RoomClass submittedRoomClass : roomClasses) {
                String roomClassName = submittedRoomClass.getName();
                RoomClass actualRoomClass = dao.getByName(roomClassName)
                        .orElseThrow(() -> new ServiceException("Room class not found: " + roomClassName));
                BigDecimal basicRate = submittedRoomClass.getBasicRate();
                BigDecimal ratePerPerson = submittedRoomClass.getRatePerPerson();
                actualRoomClass.setBasicRate(basicRate);
                actualRoomClass.setRatePerPerson(ratePerPerson);
                dao.save(actualRoomClass);
            }

            daoHelper.endTransaction();
        } catch (Exception e) {
            try {
                daoHelper.cancelTransaction();
            } catch (DaoException daoException) {
                throw new ServiceException(daoException.getMessage(), e);
            }
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
