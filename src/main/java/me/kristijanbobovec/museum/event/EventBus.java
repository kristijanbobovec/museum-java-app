package me.kristijanbobovec.museum.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum EventBus {
    INSTANCE;
    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    public <T> Runnable subscribe(Class<T> event, Consumer<T> func) {
       listeners.putIfAbsent(event, new ArrayList<>());
       List<Consumer<?>> handlers = listeners.get(event);
       handlers.add(func);
       return () -> handlers.remove(func);
    }

    public <T> void publish(T event) {
        if (!listeners.containsKey(event.getClass())) return;

        listeners.get(event.getClass()).forEach(
            h -> ((Consumer<T>) h).accept(event)
        );
    }
}
