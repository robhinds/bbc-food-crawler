# bbc-food-crawler

On news of BBC potentially shutting down their Food/recipe site, I thought it might be a fun Sunday morning task to crawl the site and download the recipes.

This uses groovy and Crawler4J to crawl and then saves JSON files to disk for each recipe - including title, desciption, ingredients (by section), method and other metadata such as preptime, serves number, other tags etc.

"gradle build" will build an uber, self-executing, JAR with all dependencies included, which can then be run with:

java -jar build/libs/bbc-food-crawler.jar

