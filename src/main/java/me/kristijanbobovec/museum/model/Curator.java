package me.kristijanbobovec.museum.model;

import java.util.List;
import java.util.Objects;

public final class Curator extends Person {
    private int idCurator;

    private Museum museum;

    private List<MuseumCollection> museumCollections;

    public Curator(String firstName, String lastName, Museum museum) {
        super(firstName, lastName);
        this.museum = museum;
    }

    public Curator(int idCurator, String firstName, String lastName, Museum museum) {
        this(firstName, lastName, museum);
        this.idCurator = idCurator;
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
