package me.kristijanbobovec.museum.model;

import java.util.List;
import java.util.Objects;

public final class Curator extends Person {
    private int idCurator;
    private int museumId;
    private Museum museum;

    private List<MuseumCollection> museumCollections;

    public Curator(String firstName, String lastName, int museumId) {
        super(firstName, lastName);
        this.museumId = museumId;
    }

    public Curator(int idCurator, String firstName, String lastName, int museumId) {
        this(firstName, lastName, museumId);
        this.idCurator = idCurator;
    }

    public Curator(String firstName, String lastName, Museum museum) {
        this(firstName, lastName, museum.getIdMuseum());
        this.museum = museum;
    }

    public Curator(int idCurator, String firstName, String lastName, Museum museum) {
        this(idCurator, firstName, lastName, museum.getIdMuseum());
        this.museum = museum;
    }

    public int getIdCurator() {
        return idCurator;
    }

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    public List<MuseumCollection> getMuseumCollections() {
        return museumCollections;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setMuseumCollections(List<MuseumCollection> museumCollections) {
        this.museumCollections = museumCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Curator curator)) return false;
        return idCurator == curator.idCurator;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCurator);
    }

    @Override
    public String toString() {
        return "Curator{" +
                "idCurator=" + idCurator +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", museum=" + museum +
                ", museumCollections=" + museumCollections +
                '}';
    }
}
