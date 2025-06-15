package org.example.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubscriber {
    String subScriberID;
    List<Publisher> subscriptions = new ArrayList<>();

    public AbstractSubscriber(String subScriberID) {
        this.subScriberID = subScriberID;
    }

    public String getSubScriberID() {
        return subScriberID;
    }
}
