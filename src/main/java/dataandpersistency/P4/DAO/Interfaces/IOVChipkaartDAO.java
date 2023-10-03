package dataandpersistency.P4.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P4.Models.OVChipkaart;
import dataandpersistency.P4.Models.Reiziger;

public interface IOVChipkaartDAO {
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    boolean save(List<OVChipkaart> ovChipkaarten) throws SQLException;
    boolean update(List<OVChipkaart> ovChipkaarten) throws SQLException;
}
