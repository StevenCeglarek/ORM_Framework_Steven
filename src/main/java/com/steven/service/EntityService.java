package com.steven.service;

import com.steven.util.ConnectionFactory;
import com.steven.util.EntityManager;
import com.steven.util.ThreadFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class EntityService {

    EntityManager em = new EntityManager();
//    ConnectionFactory util = ConnectionFactory.getInstance();
    ThreadFactory util = new ThreadFactory();

    public void save(Object o) {
        Map<String, ArrayList<String>> data = em.extractData(o);
        String tableName = em.getTableName(o);
        Callable<String> call = () -> em.saveToDb(data, tableName);
        util.getThreadExecutor().submit(call);
    }


    public void deleteTable(String tableName) {
        Callable<Boolean> call = () -> em.deleteTableFromDb(tableName);
        util.getThreadExecutor().submit(call);
    }

    public void deleteRowById(Object o, int id) {
        Callable<Boolean> call = () -> em.deleteRowByIdFromDb(em.getTableName(o), id);
        util.getThreadExecutor().submit(call);
    }

    public Future<ArrayList<Object>> findAll(Object o) {
        String tableName = em.getTableName(o);
        Callable<ArrayList<Object>> call = () -> em.findAllFromTable(tableName, o);
        return util.getThreadExecutor().submit(call);
    }

    public void update(Object o, int id, String columnName, Object value) {
//        TODO:Try and find a better way to get columnName
        Map<String, ArrayList<String>> data = em.extractData(o);
        String tableName = em.getTableName(o);
        ArrayList<String> values = data.get("Values");
        Callable call = () -> em.updateOne(tableName, columnName, id, value);
        util.getThreadExecutor().submit(call);
    }

    public Future<Object> findById(int id, Object o) {
        String tableName = em.getTableName(o);
        Callable<Object> call = () -> em.findByIdDb(tableName, id, o);
        return util.getThreadExecutor().submit(call);
    }

    public void shutdownThread() {
        util.getThreadExecutor().shutdown();
    }
}
