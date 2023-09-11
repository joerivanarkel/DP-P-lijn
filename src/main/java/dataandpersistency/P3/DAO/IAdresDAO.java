package dataandpersistency.P3.DAO;

import dataandpersistency.P3.Models.Adres;
import dataandpersistency.P3.Models.Reiziger;

import java.util.List;

public interface IAdresDAO extends IDAO<Adres> {
    Adres findByReiziger(Reiziger reiziger);
}
