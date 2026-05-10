package me.kristijanbobovec.museum.mapper;

import me.kristijanbobovec.museum.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    private UserMapper() {}

    public static User mapFromRepo(ResultSet rs) throws SQLException {
         return new User(
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7));
    }
}
