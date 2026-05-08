package me.kristijanbobovec.museum.model;

import java.util.Objects;

public class Role {
    private int idRole;
    private RoleType roleType;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role(int idRole, RoleType roleType) {
        this(roleType);
        this.idRole = idRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", roleType=" + roleType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;
        return idRole == role.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRole);
    }
}
