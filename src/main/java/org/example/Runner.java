package org.example;

import org.example.ObserverPattern.*;

public class Runner {
    public static void main(String[] args) {
        NewsAgency dailyWire = new NewsAgency("DailyWire", "pubID-1");
        NewsAgency bbc = new NewsAgency("bbc", "pubID-2");
        NewsAgency cnn = new NewsAgency("CNN", "pubID-3");
        NewsAgency reuters = new NewsAgency("Reuters", "pubID-4");

        NewsSubscriber jessiyaJoy = new NewsSubscriber("jessiyaJoy");
        NewsSubscriber gokulSree = new NewsSubscriber("gokulSree");
        NewsSubscriber alexSmith = new NewsSubscriber("alexSmith");
        NewsSubscriber mariaGarcia = new NewsSubscriber("mariaGarcia");

        jessiyaJoy.subscribe(bbc);
        gokulSree.subscribe(dailyWire);
        alexSmith.subscribe(cnn);
        mariaGarcia.subscribe(reuters);
        mariaGarcia.subscribe(bbc); // Subscribing to multiple sources


        /*
         * Articles
         * */
        Podcast podcast = new Podcast("China, DeepSeek and Chatgpt", "Simba", ArticleSize.IN_DEPTH, "POD CAST STARTING");
        TextArticle article = new TextArticle("Airplane Crash; Boeing obviously", "Ragi", ArticleSize.MICRO, "TEXT - 123");
        Podcast video = new Podcast("New Mars Rover Landing", "NASA Team", ArticleSize.LARGE, "POD CAST STARTING - 123");
        TextArticle breakingNews = new TextArticle("Election Results Announced", "Political Desk", ArticleSize.LARGE, "A LOT OF TEXT");
        Podcast techPodcast = new Podcast("AI Revolution", "Tech Team", ArticleSize.IN_DEPTH, "POD CAST STARTING - abc");

        /*
         * Items being dispatched
         * */
        ItemPublished bbcNewsItem = new ItemPublished(podcast, bbc.getPublisherName());
        ItemPublished dailyWireItem = new ItemPublished(article, dailyWire.getPublisherName());
        ItemPublished cnnItem = new ItemPublished(video, cnn.getPublisherName());
        ItemPublished reutersBreaking = new ItemPublished(breakingNews, reuters.getPublisherName());
        ItemPublished bbcTechItem = new ItemPublished(techPodcast, bbc.getPublisherName());

        /*
         * Publish by various sources
         * */
        bbc.publish(bbcNewsItem);
        dailyWire.publish(dailyWireItem);
        cnn.publish(cnnItem);
        reuters.publish(reutersBreaking);

        // Test unsubscribing
        mariaGarcia.removeSub(bbc);
        bbc.publish(bbcTechItem); // Maria won't receive this after unsubscribing

        // Test multiple subscriptions
        alexSmith.subscribe(reuters);
        reuters.publish(new ItemPublished(
                new TextArticle("Stock Market Update", "Finance Desk", ArticleSize.LARGE, "MORE TEXT AGAIN???????"),
                reuters.getPublisherName()
        ));

        // Test publishing to agency with no subscribers
        NewsAgency foxNews = new NewsAgency("Fox News", "pubID-5");
        foxNews.publish(new ItemPublished(
                new TextArticle("Political Debate Highlights", "News Team", ArticleSize.LARGE, "SAMPLE 123 123 abc"),
                foxNews.getPublisherName()
        ));

        // Test different article sizes
        dailyWire.publish(new ItemPublished(
                new TextArticle("Quick Weather Update", "Meteorology Dept", ArticleSize.MICRO, "ABC ABC ABC XYZ"),
                dailyWire.getPublisherName()
        ));

        bbc.publish(new ItemPublished(
                new Podcast("Documentary: Ocean Exploration", "Nature Team", ArticleSize.IN_DEPTH, "POD CAST STARTING - 123abc"),
                bbc.getPublisherName()
        ));
    }
}