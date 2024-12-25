package cn.com.tcsl.fast.kds.server.event.impl;


import cn.com.tcsl.fast.kds.server.event.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherImpl implements EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    @Override
    public void publish(ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}