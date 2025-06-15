package org.example.ObserverPattern;

import java.util.List;

public interface Publisher {
    void publish(ItemPublished itemPublished);
    void addSub(Subscriber subscriber);
    void removeSub(Subscriber subscriber);
    List<Subscriber> showSubscribers();
    int getSubCount();
}
