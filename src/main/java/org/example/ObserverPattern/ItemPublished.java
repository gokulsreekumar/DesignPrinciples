package org.example.ObserverPattern;

public class ItemPublished implements PublishableNewsItem {
    AbstractArticle article;
    String publisherName;

    public ItemPublished(AbstractArticle article, String publisherName) {
        this.article = article;
        this.publisherName = publisherName;
    }

    @Override
    public String name() {
        // Always uses article name as name
        return article.getName();
    }

    public AbstractArticle getArticle() {
        return article;
    }

    @Override
    public String getPublisherName() {
        return publisherName;
    }
}
