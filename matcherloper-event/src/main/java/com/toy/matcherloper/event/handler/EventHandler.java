package com.toy.matcherloper.event.handler;

import com.toy.matcherloper.event.message.Event;
import net.jodah.typetools.TypeResolver;

public interface EventHandler<T> {
    void handle(T event);

    default boolean canHandle(Event event) {
        final Class<?>[] typeArgs = TypeResolver.resolveRawArguments(
                EventHandler.class, this.getClass());
        return typeArgs[0].isAssignableFrom(event.getClass());
    }
}
