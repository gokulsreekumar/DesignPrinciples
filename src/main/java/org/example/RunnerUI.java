package org.example;

import org.example.ObserverPattern.*;
import javax.swing.*;

public class RunnerUI {
    public static void main(String[] args) {
        // Create news agencies
        NewsAgency dailyWire = new NewsAgency("DailyWire", "pubID-1");
        NewsAgency bbc = new NewsAgency("BBC", "pubID-2");

        // Create subscribers
        NewsSubscriber jessiyaJoy = new NewsSubscriber("subID-1");
        NewsSubscriber gokulSree = new NewsSubscriber("subID-2");

        // Setup subscriptions
        jessiyaJoy.subscribe(bbc);
        gokulSree.subscribe(dailyWire);

        // Main menu
        while (true) {
            String[] options = {
                    "Publish Podcast (BBC)",
                    "Publish Text Article (DailyWire)",
                    "Show Subscriber Notifications",
                    "Exit"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "News Agency System",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0: // Publish Podcast
                    String podcastTitle = JOptionPane.showInputDialog("Enter podcast title:");
                    String podcastAuthor = JOptionPane.showInputDialog("Enter author:");
                    ArticleSize podcastSize = selectArticleSize();

                    Podcast podcast = new Podcast(
                            podcastTitle,
                            podcastAuthor,
                            podcastSize,
                            "PODCAST CONTENT PLACEHOLDER<>"
                    );

                    ItemPublished bbcNewsItem = new ItemPublished(
                            podcast,
                            bbc.getPublisherName()
                    );
                    bbc.publish(bbcNewsItem);
                    break;

                case 1: // Publish Text Article
                    String articleTitle = JOptionPane.showInputDialog("Enter article title:");
                    String articleAuthor = JOptionPane.showInputDialog("Enter author:");
                    ArticleSize articleSize = selectArticleSize();

                    TextArticle article = new TextArticle(
                            articleTitle,
                            articleAuthor,
                            articleSize,
                            "Sample Text Placeholder <>"
                    );

                    ItemPublished dailyWireItem = new ItemPublished(
                            article,
                            dailyWire.getPublisherName()
                    );
                    dailyWire.publish(dailyWireItem);
                    break;

                case 2: // Show notifications
                    StringBuilder notifications = new StringBuilder();
                    notifications.append("Jessiya Joy's notifications:\n");
                    notifications.append(jessiyaJoy.getNotifications());
                    notifications.append("\n\nGokul Sree's notifications:\n");
                    notifications.append(gokulSree.getNotifications());

                    JOptionPane.showMessageDialog(
                            null,
                            notifications.toString(),
                            "Subscriber Notifications",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    break;

                case 3: // Exit
                    System.exit(0);

                default: // Closed dialog
                    System.exit(0);
            }
        }
    }

    private static ArticleSize selectArticleSize() {
        ArticleSize[] sizes = ArticleSize.values();
        return (ArticleSize) JOptionPane.showInputDialog(
                null,
                "Select article size:",
                "Article Size",
                JOptionPane.QUESTION_MESSAGE,
                null,
                sizes,
                sizes[0]
        );
    }
}