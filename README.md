# recsforme
A media recommendation web app.

## Installation

Follow these directions to get a copy of this repository on your local machine for development and testing purposes. If you just want to use the app, it will be deployed online when it's ready.

### Prerequisites

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Java EE 7 API, and (for testing) JUnit 4.12 + Hamcrest 1.3 (included in [NetBeans](https://netbeans.org/downloads/))
* [GlassFish Server 4.1.1](https://javaee.github.io/glassfish/download) (can also be installed with NetBeans)
* npm (installed with [Node.js](https://nodejs.org/en/download/))
* [api-omdb](https://github.com/Omertron/api-omdb) + dependencies
* [oxipng](https://github.com/shssoichiro/oxipng#installing) (optional)

### Installing

    cd *insert the directory you want to put the files in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install

You can then open the project in NetBeans and run. I'm not using a POM, so you'll need to manually copy any Maven dependencies, sorry.

If you don't like NetBeans, feel free to use the IDE of your choice. You can also set up GlassFish server manually if you're into that.

## Contributing

Please read the [contributing](./CONTRIBUTING.md) and [code of conduct](./CODE_OF_CONDUCT.md) files for details on the code of conduct, and the process for submitting pull requests and issues.

## Versioning

[Semantic Versioning](http://semver.org/) is used for versioning. For the versions available, see the [tags](https://github.com/bobsmith947/recsforme/tags). Currently, recsforme is in early development, and there are no releases.

## Authors

* **[Lucas Kitaev](https://github.com/bobsmith947)** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi**, **Noah Tewahade**, and **Autumn Sears** - *Support Advisors*

## License

recsforme is open source, licensed under Apache License 2.0. See the [license](./LICENSE) file for details.

## Acknowledgments

* Anyone whose code was used
* Anyone who [contributed](https://github.com/bobsmith947/recsforme/contributors) code
* Anyone who so graciously [donated](https://bobsmith947.github.io/donate.html)
* Friends and family
