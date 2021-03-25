package com.steven.test;

import com.steven.service.EntityService;
import com.steven.test.model.Console;
import com.steven.test.model.VideoGame;
import com.steven.util.EntityManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDriver {


    public static void main(String[] args) {
//        Reflections r = new Reflections();
//        Set<Class<?>> classes = r.getTypesAnnotatedWith(Entity.class);
//
//        Object[] objectArray = classes.toArray();
//
//        for (Object o : objectArray) {
//            System.out.println(o.toString());
//        }
//////
////        VideoGame vg = new VideoGame();
////
////        System.out.println(vg.getClass().getName());
//
//
//        for (Class c: classes) {
//            for (Field f: c.getDeclaredFields()) {
//                MyColumn column = f.getAnnotation(MyColumn.class);
//                if (column != null) {
//                    System.out.println(column.name());
//                }
//            }
//        }

        EntityService es = new EntityService();

        VideoGame vg = new VideoGame();
//
//        vg.setId(5);
        vg.setTitle("Call of Duty: WW2 2");
        vg.setYear("2021");
        vg.setPrice(59.99);
        vg.setConsole("Playstation 5");

        Console c = new Console();
        c.setConsoleName("Nintendo Switch");
        c.setNumber(561l);
        c.setPrice(299.99);

//        em.deleteTable("consoles");
//        em.deleteTable("video_games");
//        em.update(c, 2, "Price", 399.99);

//        Long number = 500l;
//        System.out.println(number.toString());

//        em.save(c);
        es.save(vg);

//        System.out.println(em.findById(1, c));
////
//        ArrayList<Object> all = es.findAll(vg);
//        VideoGame newVg = (VideoGame) all.get(0);
//        System.out.println(newVg);
//        Object o = new VideoGame();
//        for (Method method : o.getClass().getMethods()) {
//                    if (method.getName().startsWith("set")) {
//                        Object value = method.invoke(o);
//                        if (value != null) {
//                            System.out.println(method.getName() + "=" + value);
//                        }
//                    }
//                }
//

//        System.out.println(vg.toString());
//        String newString = vg.toString();
//        String[] newStringArray = newString.split(", ");
//        ArrayList<String> fieldValueList = new ArrayList<>();
//        for (int i = 1; i < newStringArray.length; i++) {
////            System.out.println(newStringArray[i]);
//            String[] fieldArray = newStringArray[i].split("=");
//            if(i == newStringArray.length-1) {
//                fieldArray[1] = fieldArray[1].replaceAll("}", "");
//            }
//            for (int j = 1; j < fieldArray.length; j++) {
//                fieldValueList.add(fieldArray[j]);
//            }
//        }
//        System.out.println(fieldValueList.toString());

//        Method[] methods = c.getClass().getMethods();
//        List<Method> methodList = Arrays.stream(methods).collect(Collectors.toList());
//
//        methodList.stream().forEach(System.out::println);
//
//        em.createTable(c, "consoles");

//        em.createTable(vg, "video_games");

//        vg.setId(1);
//        vg.setTitle("Halo 5");

//        for (Field f: VideoGame.class.getDeclaredFields()) {
//            Column column = f.getAnnotation(Column.class);
//            if (column != null)
//                System.out.println(column.name());
//        }

//        System.out.println(vg.getClass().getAnnotation(MyColumn.class).type());
        List<Field> fields = Arrays.stream(c.getClass().getDeclaredFields()).collect(Collectors.toList());
//        System.out.println(fields.toString());


//        for(Field f : fields) {
//            String fieldName = f.toString();
//            String[] splits = fieldName.split("\\.");
//            String endName = splits[2];
//            endName = endName.replaceAll("\\scom", "");
//            System.out.println(endName);
//
//        }
//        ArrayList<String> fieldNames = new ArrayList<>();
//        for(Field f : fields) {
//            String fieldName = f.toString();
//            String[] splits = fieldName.split("\\.");
//            String endName = splits[splits.length - 1];
//            fieldNames.add(endName);
//        }
//        ArrayList<String> fieldTypes = new ArrayList<>();
////        System.out.println(fieldTypes.toString());
//        for(Field f : fields) {
//            String fieldType = f.toString();
//            String[] splits = fieldType.split("\\.");
//            String endName = splits[2];
//            endName = endName.replaceAll("\\scom", "");
//            fieldTypes.add(endName);
////            if (!endName.equals("Integer") || !endName.equals("Double") || !endName.equals("Float")
////                    || !endName.equals("String") || !endName.equals("Boolean") || !endName.equals("Long") || !endName.equals("Short")
////             || !endName.equals("Byte") || !endName.equals("Character")) {
////                fieldType = f.toString();
////                splits = fieldType.split("\\.");
////                endName = splits[0];
////                endName = endName.replaceAll("\\scom", "");
////                fieldTypes.add(endName);
////            }
////            System.out.println(endName);
//        }
//
//        System.out.println(set.toArray().toString());
//        ArrayList<String> nameType = new ArrayList<>();
//        for (int i = 0; i < fieldTypes.size(); i++) {
//            nameType.add(fieldNames.get(i));
//            nameType.add(em.checkTypes(fieldTypes.get(i)));
//        }
//        System.out.println(nameType.toString());
    }
}
