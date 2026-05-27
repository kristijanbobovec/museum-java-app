package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.repository.*;
import me.kristijanbobovec.museum.util.SQLQueries;

import java.util.HashMap;
import java.util.Map;

public enum StatementFactory {
    INSTANCE;

    private static final String GET_ALL = "getAll";
    private static final String GET_BY_ID = "getById";
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";

    StatementFactory() {
        initHashMap();
    }

    private final Map<Class<?>, Map<String, String>> statements = new HashMap<>();

    private void initHashMap() {
        statements.put(CountryRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_COUNTRIES,
                GET_BY_ID, SQLQueries.GETBYID_COUNTRY,
                INSERT, SQLQueries.INSERT_COUNTRY,
                DELETE, SQLQueries.DELETE_COUNTRY,
                UPDATE, SQLQueries.UPDATE_COUNTRY
        )));

        statements.put(MuseumRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_MUSEUMS,
                GET_BY_ID, SQLQueries.GETBYID_MUSEUM,
                INSERT, SQLQueries.INSERT_MUSEUM,
                DELETE, SQLQueries.DELETE_MUSEUM,
                UPDATE, SQLQueries.UPDATE_MUSEUM
        )));

        statements.put(ExhibitRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_EXHIBITS,
                GET_BY_ID, SQLQueries.GETBYID_EXHIBIT,
                INSERT, SQLQueries.INSERT_EXHIBIT,
                DELETE, SQLQueries.DELETE_EXHIBIT,
                UPDATE, SQLQueries.UPDATE_EXHIBIT
        )));


        statements.put(MuseumCollectionRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_MUSEUMCOLLECTIONS,
                GET_BY_ID, SQLQueries.GETBYID_MUSEUMCOLLECTION,
                INSERT, SQLQueries.INSERT_MUSEUMCOLLECTION,
                DELETE, SQLQueries.DELETE_MUSEUMCOLLECTION,
                UPDATE, SQLQueries.UPDATE_MUSEUMCOLLECTION
        )));

        statements.put(UserRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_USERS,
                GET_BY_ID, SQLQueries.GETBYID_USER,
                INSERT, SQLQueries.INSERT_USER,
                DELETE, SQLQueries.DELETE_USER,
                UPDATE, SQLQueries.UPDATE_USER
        )));

        statements.put(CityRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_CITIES,
                GET_BY_ID, SQLQueries.GETBYID_CITY,
                INSERT, SQLQueries.INSERT_CITY,
                DELETE, SQLQueries.DELETE_CITY,
                UPDATE, SQLQueries.UPDATE_CITY
        )));

        statements.put(CuratorRepository.class, new HashMap<>(Map.of(
                GET_ALL, SQLQueries.GETALL_CURATORS,
                GET_BY_ID, SQLQueries.GETBYID_CURATOR,
                INSERT, SQLQueries.INSERT_CURATOR,
                DELETE, SQLQueries.DELETE_CURATOR,
                UPDATE, SQLQueries.UPDATE_CURATOR
        )));
    }

    public String getStatement(Class<?> repo, String methodName) {
        for (Class<?> iface : repo.getInterfaces()) {
            if (statements.containsKey(iface)) {
                return statements.get(iface).get(methodName);
            }
        }
        throw new IllegalArgumentException("No statements registered for: " + repo.getName());
    }
}
