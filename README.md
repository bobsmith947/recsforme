# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

The backend is composed of a number of Java classes, beans, and servlets. The Javadoc for these can be viewed [here](http://recsfor.me/javadoc/). There are also a number of JSP's using JSTL. Bootstrap is used for styling the pages, and Knockout is used for the data model on the frontend.

## Getting Started

Want to get recsforme set up on your local machine for development and testing purposes? Great! This is how to do so:

### Prerequisites

* A JDK (at least Java 8). If you have a higher version, make sure the project is compiled on version 8.
* A servlet container like [Tomcat](https://tomcat.apache.org)
* [Apache Ant 1.7.1+ and Apache Ivy 2.4.0](https://ant.apache.org/)
* npm (comes with [Node.js](https://nodejs.org/en/download/))

**Access to a SQL database under the name *jdbc/MediaRecom* is required. You can contact me for the schema. Once set up, the connection can be added to `context.xml` (for Tomcat).**

**You can get your own API key from https://www.omdbapi.com/. Set it as an environment variable named *OMDB_KEY*.**

### Installing

    cd *directory you want to install recsforme in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install
    ant resolve

### Running

Ant tasks have already been made to do these automatically, but if you want to do it manually, make sure you have `CATALINA_HOME` set (if you're using Tomcat), and that your user has proper permissions.

    $CATALINA_HOME/bin/catalina start
    ant run -Dj2ee.server.home=$CATALINA_HOME

Unit tests can be executed with:

    ant test -Dj2ee.server.home=$CATALINA_HOME

When you're done:

    $CATALINA_HOME/bin/catalina stop

## Contributing

Please read [CONTRIBUTING](./CONTRIBUTING.md) for details on the community guidelines, and the process for submitting pull requests and issues.

## Authors

* **Lucas Kitaev** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi** - *Code Support Advisor*

## License

recsforme is open source, licensed under the Apache License 2.0. See [LICENSE](./LICENSE) for details.

## Acknowledgments

* Anyone whose code was used
* Friends and family
