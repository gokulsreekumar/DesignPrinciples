package org.example.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPublisher implements Publisher {
    public static final String ANONYMOUS = "ANONYMOUS";
    String publisherRegisteredID;
    String publisherName;
    List<Subscriber> subscribers = new ArrayList<>();

    public AbstractPublisher(String publisherRegisteredID) {
        this.publisherRegisteredID = publisherRegisteredID;
        this.publisherName = ANONYMOUS;
    }

    public AbstractPublisher(String publisherName, String publisherRegisteredID) {
        this.publisherName = publisherName;
        this.publisherRegisteredID = publisherRegisteredID;
    }

    public String getPublisherRegisteredID() {
        return publisherRegisteredID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void publish(ItemPublished itemPublished) {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers :(");
            return;
        }
        // Iterate over all subscribers and call their onEvent
        subscribers.parallelStream().forEach(subscriber -> subscriber.onEvent(itemPublished));
    }

    public void addSub(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSub(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public List<Subscriber> showSubscribers() {
        System.out.println(subscribers);
        return subscribers;
    }

    public int getSubCount() {
        return Math.toIntExact(subscribers.size());
    }
}
