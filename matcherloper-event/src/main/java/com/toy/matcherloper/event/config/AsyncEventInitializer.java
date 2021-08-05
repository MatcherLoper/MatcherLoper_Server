package com.toy.matcherloper.event.config;

import com.toy.matcherloper.event.dispatcher.Events;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

public class AsyncEventInitializer {

    @PostConstruct
    public void init() {
        Events.init(Executors.newFixedThreadPool(10));
    }

    @PreDestroy
    public void close() {
        Events.close();
    }
}
