package com.steven.service;

import com.steven.util.ConnectionFactory;
import com.steven.util.ConnectionSession;
import com.steven.util.EntityManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

public class EntityService {

    EntityManager em = new EntityManager();
    ConnectionFactory util = ConnectionFactory.getInstance();

    public void save(Object o) {
        Map<String, ArrayList<String>> data = em.extractData(o);
        String tableName = em.getTableName(o);
        Callable<String> call = () -> em.saveToDb(data, tableName);
//        sess.getThreadActivator().execute();
        util.getThreadActivator().submit(call);
        util.getThreadActivator().shutdown();
//        em.saveToDb(data, tableName);
    }


    public void deleteTable(String tableName) {
        em.deleteTableFromDb(tableName);
    }

    public void deleteRowById(Object o, int id) {
        em.deleteRowByIdFromDb(em.getTableName(o), id);
    }

    public ArrayList<Object> findAll(Object o) {
        String tableName = em.getTableName(o);
        return em.findAllFromTable(tableName, o);
    }

    public void update(Object o, int id, String columnName, Object value) {
//        TODO:Try and find a better way to get columnName
        Map<String, ArrayList<String>> data = em.extractData(o);
        String tableName = em.getTableName(o);
        ArrayList<String> values = data.get("Values");
        em.updateOne(tableName, columnName, id, value);
    }

    public Object findById(int id, Object o) {
        String tableName = em.getTableName(o);
        return em.findByIdDb(tableName, id, o);
    }
}
