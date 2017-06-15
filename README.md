# Groceryshopping
Webscraping using SpringBoot and PhantomJs

## Installing dependencies
Groceryshopping requires [Java 1.8](https://www.java.com/en/download/),  [PhantomJS](http://phantomjs.org/). It Also requires [Maven](https://maven.apache.org/download.cgi) to build
Make sure these are installed before proceeding

## Building
```sh
$ mvn package
```

## Excution
#### PhantomJS enviroment config

Application requires the following env variable to be set `phantomjs.binary.path`
By default the following location will be used`/usr/local/bin/phantomjs`
#### Run
```sh
$ cd target/
$ java -jar groceryshopping-1.0.jar '<URL>'
```
**Sample URL**

```http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=true&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=12518&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categoryId=185749&listId=&storeId=10151&promotionId=#langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true```

#### Run test
```sh
$ mvn test
```
