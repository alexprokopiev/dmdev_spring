package org.alexprokopiev.spring.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @EventListener
    public void AcceptEntity(EntityEvent event) {
        System.out.println("Entity: " + event);
    }
}
