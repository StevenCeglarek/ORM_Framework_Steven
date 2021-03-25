package com.steven.dao;

import java.util.ArrayList;

public interface GenericDao<T, I, S> {

    T findById(I id);

    int save(T t);

    int update(T t);

    int delete(I i);

    void deleteTable(S s);

    void deleteRowById(T t, I i);

    ArrayList<T> findAll(T t);

    void update(T t, I i, S s, T t2);

    T findById(I i, T t);



}
