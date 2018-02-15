# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

Java/SQL backend + CSS/JS frontend. Focused on as little use of web frameworks as possible, so as to provide a better learning experience.

## Getting Started

Want to get recsforme up and running on your local machine for development and testing purposes? Great! This is how to get started:

### Prerequisites

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Java EE 7 API, and (for testing) JUnit 4.12 + Hamcrest 1.3 (included in [NetBeans](https://netbeans.org/downloads/))
* [GlassFish Server 4.1.1](https://javaee.github.io/glassfish/download) (can also be installed with NetBeans)
* npm (installed with [Node.js](https://nodejs.org/en/download/))
* [api-omdb](https://github.com/Omertron/api-omdb) + dependencies
* [musicbrainzws2-java](https://code.google.com/archive/p/musicbrainzws2-java/) + dependencies
* [oxipng](https://github.com/shssoichiro/oxipng) (optional)

### Installing

    cd *insert the directory you want to put the files in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install

At the moment, I'm not using dependency management for Java, so you'll need to manually copy any JAR's. You can find the latest Java dependency package in the [releases](https://github.com/bobsmith947/recsforme/releases) section. Please note, I don't always add new dependencies, so it may not always be under the latest release.

You can then open recsforme in NetBeans, or the IDE of your choice.

recsforme can also be run directly from the command line:

    cd *your glassfish install directory*
    glassfish4/bin/asadmin start-domain domain1
    cd *your recsforme install directory*
    ant -Dj2ee.server.home=*your glassfish install directory*

When you're done:

    cd *your glassfish install directory*
    glassfish4/bin/asadmin stop-domain domain1

## Contributing

Please read the [contributing](./CONTRIBUTING.md) and [code of conduct](./CODE_OF_CONDUCT.md) files for details on the code of conduct, and the process for submitting pull requests and issues.

## Authors

* **[Lucas Kitaev](https://github.com/bobsmith947)** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi** - *Code Support Advisor*

## License

recsforme is open source, licensed under Apache License 2.0. See the [license](./LICENSE) file for details.

## Acknowledgments

* Anyone whose code was used
* Anyone who [contributed](https://github.com/bobsmith947/recsforme/contributors) code
* Anyone who so graciously [donated](https://bobsmith947.github.io/donate.html)
* Friends and family
