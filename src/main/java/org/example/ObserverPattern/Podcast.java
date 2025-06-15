package org.example.ObserverPattern;

public class Podcast extends AbstractArticle implements Listenable {
    String content;
    int durationInMinutes;

    public Podcast(String name, String author, ArticleSize articleSize, String content) {
        super(name, author, articleSize, ArticleType.PODCAST);
        this.content = content;
    }

    @Override
    public void listen() {
        System.out.println("PODCAST: " + getName() + " BY " + getAuthor());
        System.out.println("DURATION: " + durationInMinutes + " minutes");
        System.out.println("Let us listen to the show..." + content);
    }

    void accept(NewsVisitor newsVisitor) {
        newsVisitor.visit(this);
    }
}
