package me.kristijanbobovec.museum.model;

import java.util.Objects;

public final class User extends Person {
    private int idUser;
    private String username;
    private String email;
    private String password;
    private int roleId;

    public User(int idUser, String username, String email, String firstName, String lastName, int roleId) {
        super(firstName, lastName);
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.idUser = idUser;
    }

    public User(String username, String email, String firstName, String lastName, int roleId) {
        super(firstName, lastName);
        this.username = username;
        this.email = email;
        this.roleId = roleId;
    }

    public User(String username, String email, String firstName, String lastName, String password, int roleId) {
        this(username, email, firstName, lastName, roleId);
        this.password = password;
    }

    public User(int idUser, String username, String email, String firstName, String lastName, String password, int roleId) {
        this(idUser, username, email, firstName, lastName, roleId);
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return idUser == user.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUser);
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
