package service.impl;

import builder.Builder;
import dao.DaoHelper;
import dao.api.UserDao;
import entity.User;
import exception.DaoException;
import exception.ServiceException;
import service.api.UserService;
//import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private DaoHelper daoHelper;
    private Builder<User> builder;

    public UserServiceImpl(DaoHelper daoHelper, Builder<User> builder) {
        this.daoHelper = daoHelper;
        this.builder = builder;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException {
        try {
            UserDao dao = daoHelper.userDao(builder);
            return dao.getByEmailAndPassword(email, password);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
