package dataandpersistency.P5.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {
    boolean save(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    List<T> findAll() throws SQLException;
}
