package dataandpersistency.P3.DAO;

import dataandpersistency.P3.Models.Reiziger;

import java.util.ArrayList;

public interface IReizigerDAO extends IDAO<Reiziger> {
    Reiziger findById(int id);
    ArrayList<Reiziger> findByGbdatum(String datum);
}
