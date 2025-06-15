package org.example.ObserverPattern;

public interface NewsVisitor {
    void visit(TextArticle article);
    void visit(Podcast podcast);
    void visit(MultiMediaArticle multiMediaArticle);
}
