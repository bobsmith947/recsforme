# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

Java/SQL backend + CSS/JS frontend. Focused on as little use of frameworks as possible, so as to provide a better learning experience.

## Getting Started

Want to get recsforme set up on your local machine for development and testing purposes? Great! This is how to do so:

### Prerequisites

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Ant 1.7.1+ and Apache Ivy 2.4.0](https://ant.apache.org/)
* [GlassFish Server 4.1.1](https://javaee.github.io/glassfish/download)
* npm (installed with [Node.js](https://nodejs.org/en/download/))
* [oxipng](https://github.com/shssoichiro/oxipng) (optional)

### Installing

    cd *directory you want to install recsforme in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install
    ant resolve

After everything has been installed, you can open recsforme in NetBeans, or the IDE of your choice and begin [contributing](#contributing) to this wonderful project.

### Running

Once your GlassFish install directory has been added to your PATH, recsforme can be run directly from the command line:

    cd glassfish4
    /bin/asadmin start-domain domain1
    cd *your recsforme install directory*
    ant run -Dj2ee.server.home=glassfish4/glassfish

Unit tests can be executed with:

    ant test -Dj2ee.server.home=glassfish4/glassfish

When you're done:

    cd glassfish4
    /bin/asadmin stop-domain domain1

## Contributing

Please read [CONTRIBUTING](./CONTRIBUTING.md) and [CODE OF CONDUCT](./CODE_OF_CONDUCT.md) for details on the community guidelines, and the process for submitting pull requests and issues.

## Authors

* **[Lucas Kitaev](https://github.com/bobsmith947)** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi** - *Code Support Advisor*

## License

recsforme is open source, licensed under the Apache License 2.0. See [LICENSE](./LICENSE) for details.

## Acknowledgments

* Anyone whose code was used
* Anyone who [contributed](https://github.com/bobsmith947/recsforme/contributors) code
* Anyone who so graciously [donated](https://bobsmith947.github.io/donate.html)
* Friends and family
