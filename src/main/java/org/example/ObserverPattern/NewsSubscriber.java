package org.example.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class NewsSubscriber extends AbstractSubscriber implements Subscriber, NewsVisitor {
    List<String> notifications = new ArrayList<>();
    public NewsSubscriber(String subScriberID) {
        super(subScriberID);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void onEvent(PublishableNewsItem newsItem) {
        addNewsToNotificationList(newsItem);
        consoleNotify(newsItem);
        // ACCEPTING -> VISITING -> GOING TO ARTICLE BY TYPE
        accept(newsItem);
        bye();
    }

    private static void bye() {
        System.out.println("~~~~~~~~~~~~\n\n");
    }

    private void addNewsToNotificationList(PublishableNewsItem newsItem) {
        System.out.println("\n\n~~~~~~~~~~~~");
        notifications.add(newsItem.name());
    }

    private void accept(PublishableNewsItem newsItem) {
        newsItem.getArticle().accept(this);
    }

    private void consoleNotify(PublishableNewsItem newsItem) {
        System.out.println("News alert for " + subScriberID + " !");
        System.out.println(newsItem.name());
        System.out.println("by");
        System.out.println(newsItem.getArticle().author);
        System.out.println("from");
        System.out.println(newsItem.getPublisherName());
    }

    @Override
    public void subscribe(Publisher publisher) {
        publisher.addSub(this);
    }

    @Override
    public void removeSub(Publisher publisher) {
        publisher.removeSub(this);
    }

    @Override
    public void visit(TextArticle article) {
        System.out.println("Invoking Read");
        article.read();
    }

    @Override
    public void visit(Podcast podcast) {
        System.out.println("Invoking Listen");
        podcast.listen();
    }

    @Override
    public void visit(MultiMediaArticle multiMediaArticle) {
        System.out.println("INVOKING MULTIMEDIA - BUILD IN PROGRESS");
    }
}
