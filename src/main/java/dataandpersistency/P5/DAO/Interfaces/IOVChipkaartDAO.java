package dataandpersistency.P5.DAO.Interfaces;

import java.sql.SQLException;
import java.util.List;

import dataandpersistency.P4.Models.OVChipkaart;
import dataandpersistency.P4.Models.Reiziger;

public interface IOVChipkaartDAO extends IDAO<OVChipkaart> {
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
