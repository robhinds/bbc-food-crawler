package com.bbc.food.scraper

import groovy.json.JsonBuilder
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.jsoup.Jsoup

import groovy.util.logging.Slf4j

@Slf4j
class RecipeScraper {

	static parseRecipe( String htmlDocument, String url ){
		Document doc = Jsoup.parse( htmlDocument, url )
		
		Element title = doc.select( "h1.content-title__text" ).first()
		Element description = doc.select( "p.recipe-description__text" ).first()
		
		Element metadataContainer = doc.select( "div.recipe-metadata" ).first()  
		Elements metadataElements = metadataContainer.getElementsByAttributeValueContaining( "class", "recipe-metadata__")
		Map metaData = [:] 
		metadataElements.findAll{ it.attr("itemprop") }.each { Element e ->
			metaData[ e.attr("itemprop") ] =  e.text() 
		}
		
		Elements ingredientsSubheadings = doc.select( "h3.recipe-ingredients__sub-heading" )
		Elements ingredientsLists = doc.select( "ul.recipe-ingredients__list" )
		
		Map ingredientSections = [:]
		ingredientsLists.eachWithIndex{ Element ingredientList, int index  ->
			String subsectionTitle = ingredientsSubheadings[index]?.text() ?: "section${index}"   
			Elements ingredients = ingredientList.getElementsByAttributeValueContaining( "class", "recipe-ingredients__list-item" )
			ingredientSections[subsectionTitle] = ingredients.collect{ it.text() }
		}
		
		Element methodList = doc.select( "ol.recipe-method__list" ).first()
		Elements stepList = methodList.getElementsByAttributeValueContaining( "class", "recipe-method__list-item-text" )
		List steps = stepList.collect{ it.text() }
		
		String filename = title.text().toLowerCase().replaceAll( " ", "_" ).replaceAll( /[^a-zA-Z_]/, "" ) 
		println "WRITING: /tmp/bbc_${filename}.json"
		new File( "/home/rob/dev/bbc-food/bbc_${filename}.json" ).write(
			new JsonBuilder(
				[ title: title.text(), 
					description: description.text(), 
					metaData: metaData, 
					ingredientsBySection: ingredientSections, 
					steps: steps ]
			).toPrettyString()
		)
	}
	
}
