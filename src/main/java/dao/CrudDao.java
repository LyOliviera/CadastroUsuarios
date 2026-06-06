package main.java.dao;

import java.util.List;

public interface CrudDao {
    void insert(Object horcrux);
    int updateByID(Object horcrux);
    int deleteById(Integer id);
    Object findById(Integer id, boolean ativos);
    List<Object>findAll();
}
