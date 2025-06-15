package org.example.ObserverPattern;

public interface Subscriber {
    void onEvent(PublishableNewsItem newsItem);
    void subscribe(Publisher publisher);
    void removeSub(Publisher publisher);
}
