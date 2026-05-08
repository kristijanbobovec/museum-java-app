package me.kristijanbobovec.museum.model;

import java.util.Objects;
import java.util.Optional;

public final class User extends Person {
    private int idUser;
    private String username;
    private String email;

    private String password;
    private Role role;

    public User(String username, String email, String firstName, String lastName, String password, Role role) {
        super(firstName, lastName);
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String username, String email, String firstName, String lastName, Role role) {
        super(firstName, lastName);
        this.username = username;
        this.email = email;
        this.role = role;
    }


    public User(int idUser, String username, String email, String firstName, String lastName, String password, Role role) {
        this(username, email, firstName, lastName, password, role);
        this.idUser = idUser;
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

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                '}';
    }
}
