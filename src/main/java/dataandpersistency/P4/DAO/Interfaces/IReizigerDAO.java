package dataandpersistency.P4.DAO.Interfaces;

import dataandpersistency.P4.Models.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface IReizigerDAO {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum) throws SQLException;
}
