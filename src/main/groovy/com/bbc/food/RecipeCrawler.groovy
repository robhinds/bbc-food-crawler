package com.bbc.food

import com.bbc.food.scraper.RecipeScraper

import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import java.util.regex.Pattern

class RecipeCrawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern.compile( ".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))\$" )
		
	@Override public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase()
		!FILTERS.matcher( href ).matches() && href.startsWith("http://www.bbc.co.uk/food")
	}
	
	@Override public void visit(Page page) {
		String url = page.getWebURL().getURL()
		if (page.getParseData() instanceof HtmlParseData && url.startsWith("http://www.bbc.co.uk/food/recipes/") ) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData()
			RecipeScraper.parseRecipe( htmlParseData.getHtml(), url )
		}
	}
}
