package org.example.ObserverPattern;

public interface PublishableNewsItem {
    String name();

    AbstractArticle getArticle();

    String getPublisherName();
}