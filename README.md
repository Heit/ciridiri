#ciridiri: dead simple wiki engine

ciridiri is a wiki-like web application. It is based on [circumflex][] and
ideas of [diri](http://repo.cat-v.org/diri/).

##Requirements

* maven
* [circumflex][]

##Installation

    git clone git://github.com/vast/ciridiri.git
    cd ciridiri
    mvn jetty:run

And point your browser to http://localhost:8180/

##Usage
_Note: default password is `pass`._

The usage is very simple. Create new pages through accessing `http://localhost:8180/path/to/new/page.html`.  
Edit existent page through accessing `http://localhost:8180/existent/page.html.e` or just press `ctrl-e`.

##Formatting
ciridiri proudly uses [circumflex-markdown](http://circumflex.ru/index.html#md) implementation of [markdown](http://daringfireball.net/projects/markdown/) to format pages.

##Configuration
See `src/main/resources/cx.properties`. There are few options available:

* `ciridiri.contentRoot` -- where pages will be stored;
* `ciridiri.password` -- password for pages;
* `ciriridi.caching` -- page fragments caching.

##See also
[circumflex][]: lightweight Scala-based Web application framework and ORM

[circumflex]: http://circumflex.ru/
