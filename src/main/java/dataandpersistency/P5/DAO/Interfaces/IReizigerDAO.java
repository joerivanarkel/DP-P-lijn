package dataandpersistency.P5.DAO.Interfaces;

import dataandpersistency.P4.Models.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface IReizigerDAO extends IDAO<Reiziger> {
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum) throws SQLException;
}
