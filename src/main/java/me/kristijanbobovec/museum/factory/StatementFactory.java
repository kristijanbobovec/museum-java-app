package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.repository.*;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.util.HashMap;
import java.util.Map;

public enum StatementFactory {
    INSTANCE;

    StatementFactory() {
        initHashMap();
    }

    private final Map<Class<?>, Map<String, String>> statements = new HashMap<>();

    private void initHashMap() {
        statements.put(CountryRepository.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_COUNTRIES,
                "getById", SQLQueries.GETBYID_COUNTRY,
                "insert", SQLQueries.INSERT_COUNTRY,
                "delete", SQLQueries.DELETE_COUNTRY,
                "update", SQLQueries.UPDATE_COUNTRY
        )));

        statements.put(MuseumRepository.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_MUSEUMS,
                "getById", SQLQueries.GETBYID_MUSEUM,
                "insert", SQLQueries.INSERT_MUSEUM,
                "delete", SQLQueries.DELETE_MUSEUM,
                "update", SQLQueries.UPDATE_MUSEUM
        )));

        statements.put(ExhibitRepository.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_EXHIBITS,
                "getById", SQLQueries.GETBYID_EXHIBIT,
                "insert", SQLQueries.INSERT_EXHIBIT,
                "delete", SQLQueries.DELETE_EXHIBIT,
                "update", SQLQueries.UPDATE_EXHIBIT
        )));


        statements.put(MuseumCollectionRepository.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_MUSEUMCOLLECTIONS,
                    "getById", SQLQueries.GETBYID_MUSEUMCOLLECTION,
                "insert", SQLQueries.INSERT_MUSEUMCOLLECTION,
                "delete", SQLQueries.DELETE_MUSEUMCOLLECTION,
                "update", SQLQueries.UPDATE_MUSEUMCOLLECTION
        )));

        statements.put(UserRepository.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_USERS,
                "getById", SQLQueries.GETBYID_USER,
                "insert", SQLQueries.INSERT_USER,
                "delete", SQLQueries.DELETE_USER,
                "update", SQLQueries.UPDATE_USER
        )));
    }

    public String getStatement(Class<?> repo, String methodName) {
        return statements.get(repo.getInterfaces()[0]).get(methodName);
    }
}
