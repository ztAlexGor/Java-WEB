package service.api;

import entity.User;
import exception.ServiceException;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException;

}
