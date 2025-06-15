package org.example.ObserverPattern;

public class TextArticle extends AbstractArticle implements Readable {
    String content;
    int wordCount;

    public TextArticle(String name, String author, ArticleSize articleSize, String content) {
        super(name, author, articleSize, ArticleType.TEXT);
        this.content = content;
    }

    @Override
    public void read() {
        System.out.println("ARTICLE: " + getName() + " BY " + getAuthor());
        System.out.println("DURATION: " + wordCount + " words");
        System.out.println("Let us read..." + content);
    }

    void accept(NewsVisitor newsVisitor) {
        newsVisitor.visit(this);
    }
}
