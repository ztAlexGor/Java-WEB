package dao.api;

import entity.Identifiable;
import exception.DaoException;
import java.util.List;
import java.util.Optional;


public interface Dao <T extends Identifiable> {
    Optional<T> getById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    void save(T entity) throws DaoException;

    void deleteById(int id) throws DaoException;
}
