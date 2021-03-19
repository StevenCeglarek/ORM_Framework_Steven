package com.steven.dao;

import com.steven.annotations.Id;
import com.steven.util.ConnectionFactory;
import com.steven.util.ConnectionSession;
import javafx.scene.effect.Reflection;
import org.reflections.Reflections;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoImpl implements GenericDao<Object, Integer, String> {


    @Override
    public Object findById(Integer id) {
        return null;
    }

    @Override
    public int create(Object o) {
        return 0;
    }

    @Override
    public int create(Integer i, String s) {
        Reflections r = new Reflections();
        r.getFieldsAnnotatedWith(Id.class);

        String sql = "INSERT INTO video_games(id, title) " +
                "VALUES (?, ?)";
        try (
                ConnectionSession conn = new ConnectionSession();
                PreparedStatement ps = conn.getActiveConnection().prepareStatement(sql);)
        {
            ps.setInt(1,i);
            ps.setString(2, s);

            int i2 = ps.executeUpdate();
            return i2;

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

}
