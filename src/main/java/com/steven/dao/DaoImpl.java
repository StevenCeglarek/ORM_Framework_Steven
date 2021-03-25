package com.steven.dao;

import com.steven.util.ConnectionSession;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class DaoImpl implements GenericDao<Object, Integer, String> {


    @Override
    public Object findById(Integer id) {
        return null;
    }

    @Override
    public int save(Object o) {
        return 0;
    }

    @Override
    public int update(Object o) {
        return 0;
    }

    @Override
    public int delete(Integer integer) {
        return 0;
    }

    @Override
    public void deleteTable(String s) {

    }

    @Override
    public void deleteRowById(Object o, Integer integer) {

    }

    @Override
    public ArrayList<Object> findAll(Object o) {
        return null;
    }

    @Override
    public void update(Object o, Integer integer, String s, Object t2) {

    }

    @Override
    public Object findById(Integer integer, Object o) {
        return null;
    }
}
