package dao.api;

import entity.User;
import exception.DaoException;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> getByEmailAndPassword(String email, String password) throws DaoException;

}
