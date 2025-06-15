package org.example.ObserverPattern;

public abstract class AbstractArticle {
    String name;
    String author;
    ArticleSize articleSize;
    ArticleType articleType;

    AbstractArticle (String name, String author, ArticleSize articleSize, ArticleType articleType) {
        this.name = name;
        this.author = author;
        this.articleSize = articleSize;
        this.articleType = articleType;
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    ArticleSize getArticleSize() {
        return articleSize;
    }

    ArticleType getType() {
        return articleType;
    }

    abstract void accept(NewsVisitor newsVisitor);
}
