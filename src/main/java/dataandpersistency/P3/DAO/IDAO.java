package dataandpersistency.P3.DAO;

import java.util.List;

public interface IDAO<T> {
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);
    List<T> findAll();
}
