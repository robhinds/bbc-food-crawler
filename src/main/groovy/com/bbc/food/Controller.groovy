package com.bbc.food

import edu.uci.ics.crawler4j.crawler.CrawlConfig
import edu.uci.ics.crawler4j.crawler.CrawlController
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer


public class Controller {
	
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "build"
        int numberOfCrawlers = 7

        CrawlConfig config = new CrawlConfig()
        config.setCrawlStorageFolder(crawlStorageFolder)

        PageFetcher pageFetcher = new PageFetcher(config)
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig()
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher)
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer)

		('a'..'z').each{
			controller.addSeed("http://www.bbc.co.uk/food/ingredients/by/letter/${it}".toString())
		}
		controller.addSeed("http://www.bbc.co.uk/food/ingredients/by/letter/a")

        controller.start(RecipeCrawler, numberOfCrawlers)
    }
}