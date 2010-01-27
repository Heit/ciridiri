#ciridiri: dead simple wiki engine on circumflex

ciridiri is a wiki-like web application. It is based on [circumflex](http://circumflex.ru) and
ideas of [diri](http://repo.cat-v.org/diri/).

##Requirements

* maven
* [circumflex](http://circumflex.ru)

##Installation
    git clone git://github.com/vast/ciridiri.git
    cd ciridiri
    mvn jetty:run

And point your browser to http://localhost:8180/

##Usage
Note: default password is `pass`.

The usage is very simple. Create new pages through accessing `http://localhost:8180/path/to/new/page.html`.
Edit existent page through accessing `http://localhost:8180/existent/page.html.e` or just press `ctrl-e`.

ciridiri uses [markdown](http://daringfireball.net/projects/markdown/) to format pages.

##Configuration
See `src/main/resources/cx.properties`

##See also
[circumflex](http://circumflex.ru): lightweight Scala-based Web application framework and ORM 
