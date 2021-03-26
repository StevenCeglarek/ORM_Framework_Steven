package com.steven.test;

import com.steven.service.EntityService;
import com.steven.test.models.Console;
import com.steven.test.models.VideoGame;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestDriver {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EntityService es = new EntityService();
        VideoGame vg = new VideoGame();
        vg.setTitle("Call of Duty: Black ops 9");
        vg.setYear("2030");
        vg.setPrice(89.99);
        vg.setConsole("Playstation 6");
        VideoGame vg2 = new VideoGame();
        vg2.setTitle("Call of Duty: Modern Warfare 8");
        vg2.setYear("2035");
        vg2.setPrice(99.99);
        vg2.setConsole("Nintendo Ultimate");
        Console c = new Console();
        c.setConsoleName("Nintendo 64 Mini the next one");
        c.setNumber(954305l);
        c.setPrice(99.99);
        System.out.println(c.toString());
        Console c2 = new Console();
        c2.setConsoleName("Playstation 2");
        c2.setNumber(305954l);
        c2.setPrice(69.99);
        es.save(c);
        es.save(c2);
        es.save(vg);
        es.save(vg2);
        es.update(new Console(), 3, "Price", 299.99);
        es.update(new VideoGame(), 1, "console", "Xbox One");
        Future<Object> futureObj = es.findById(1, new Console());
        Console c3 = (Console) futureObj.get();
        System.out.println(c3);
        Future<ArrayList<Object>> futureAll = es.findAll(vg);
        ArrayList<Object> all = futureAll.get();
        VideoGame newVg = (VideoGame) all.get(1);
        System.out.println(newVg);
        es.shutdownThread();
    }
}
