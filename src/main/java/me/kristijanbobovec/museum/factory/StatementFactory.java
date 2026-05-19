package me.kristijanbobovec.museum.factory;

import me.kristijanbobovec.museum.repository.CountryRepositoryImpl;
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
        statements.put(CountryRepositoryImpl.class, new HashMap<>(Map.of(
                "getAll", SQLQueries.GETALL_COUNTRIES
        )));
    }

    public <T> String getStatement(T repo, String methodName) {
        return statements.get(repo).get(methodName);
    }
}
