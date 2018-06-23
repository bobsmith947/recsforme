# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

Java/SQL backend + CSS/JS frontend. Documentation can be viewed [here](http://recsfor.me/javadoc/).

## Getting Started

Want to get recsforme set up on your local machine for development and testing purposes? Great! This is how to do so:

### Prerequisites

* A JDK (at least Java 8)
* A servlet container like [Tomcat](https://tomcat.apache.org)
* [Apache Ant 1.7.1+ and Apache Ivy 2.4.0](https://ant.apache.org/)
* npm (comes with [Node.js](https://nodejs.org/en/download/))
* [oxipng](https://github.com/shssoichiro/oxipng) (optional)

**Access to a SQL database is required. You can contact me for the schema.**

**You may also want to get your own API key from https://www.omdbapi.com/.**

### Installing

    cd *directory you want to install recsforme in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install
    ant resolve

After everything has been installed, you can open recsforme in NetBeans, or the IDE of your choice, and begin [contributing](#contributing) to this wonderful project.

### Running

It's possible to run recsforme without an IDE. Make sure you have `CATALINA_HOME` set, and that your user has proper permissions.

    $CATALINA_HOME/bin/catalina start
    ant run -Dj2ee.server.home=$CATALINA_HOME

Unit tests can be executed with:

    ant test -Dj2ee.server.home=$CATALINA_HOME

When you're done:

    $CATALINA_HOME/bin/catalina stop

Alternatively, you can directly deploy the WAR (can be downloaded from the [releases](https://github.com/bobsmith947/recsforme/releases) section) using the Tomcat Manager (you will need to set up a user with the `manager-gui` permission). Keep in mind, the latest release isn't the latest build, so you'll be missing any hot new features.

## Contributing

Please read [CONTRIBUTING](./CONTRIBUTING.md) and [CODE OF CONDUCT](./CODE_OF_CONDUCT.md) for details on the community guidelines, and the process for submitting pull requests and issues.

## Authors

* **Lucas Kitaev** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi** - *Code Support Advisor*

## License

recsforme is open source, licensed under the Apache License 2.0. See [LICENSE](./LICENSE) for details.

## Acknowledgments

* Anyone whose code was used
* Anyone who [contributed](https://github.com/bobsmith947/recsforme/contributors) code
* Anyone who so graciously [donated](https://bobsmith947.github.io/donate.html)
* Friends and family
