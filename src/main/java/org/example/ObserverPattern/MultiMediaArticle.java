package org.example.ObserverPattern;

public class MultiMediaArticle extends AbstractArticle implements Readable, Listenable {
    String text;
    String audio;

    MultiMediaArticle(String name, String author, ArticleSize articleSize) {
        super(name, author, articleSize, ArticleType.MULTIMEDIA);
    }

    @Override
    public void listen() {
        System.out.println("AUDIO PART: " + audio);
    }

    @Override
    public void read() {
        System.out.println("TEXT PART: " + text);
    }

    void accept(NewsVisitor newsVisitor) {
        newsVisitor.visit(this);
    }
}
