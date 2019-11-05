# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

The backend is composed of Java classes, beans, and servlets. The Javadoc for these can be viewed [here](https://bobsmith947.github.io/javadoc/). There are also a number of JavaServer Pages (JSP) as well as a SQL database. Bootstrap is used for styling the pages, and Knockout is used for managing states.

## Getting Started

Want to get recsforme set up on your local machine for development and testing purposes? Great! This is how to do so:

### Prerequisites

* JDK (at least Java 8)
* Tomcat server ([Tomcat](https://tomcat.apache.org) 8.5 is recommended)
* SQL database (template is provided)
* [Apache Ant and Apache Ivy](https://ant.apache.org/)
* npm (comes with [Node.js](https://nodejs.org/en/download/))
* [An OMDb API key](https://www.omdbapi.com/) (set it as an environment variable named *OMDB_KEY*)

#### Setting up Tomcat and Ant

1. Set the `CATALINA_HOME` environment variable to point to the Tomcat installation directory
2. Add a user to `$CATALINA_HOME/conf/tomcat-users.xml`
    ```
    <role rolename="manager-script" />
    <user username="script" password="password" roles="manager-script" />
    ```
    You may use a different username/password if you also change the values of `tomcat.username` and `tomcat.password` in `nbproject/project.properties`
3. Copy database connection driver to `$CATALINA_HOME/lib`
4. Add database connection info to `$CATALINA_HOME/conf/context.xml` or `web/META-INF/context.xml`
    ```
    <Resource name="jdbc/MediaRecom" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="" password="" driverClassName=""
               url=""/>
    ```
5. Copy/link the Ivy .jar file as well as the contents of `$CATALINA_HOME/lib` to the Ant `lib` directory

### Installing

    cd *directory you want to install recsforme in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install
    ant resolve

### Running

Start the server:

    $CATALINA_HOME/bin/catalina start

Build and deploy the application:    

    ant run
    
Undeploy the application:

    ant run-undeploy
    
Cleanup build:

    ant clean

Run unit tests:

    ant test

Stop the server:

    $CATALINA_HOME/bin/catalina stop

## Contributing

Please read [CONTRIBUTING](./CONTRIBUTING.md) for details on the community guidelines, and the process for submitting pull requests and issues.

## Authors

* **Lucas Kitaev** - *Lead Developer and Designer*
* **Oleg Kitaev** - *Expert Advisor*
* **Sai Donepudi** - *Support Advisor*

## License

recsforme is open source, licensed under the Apache License 2.0. See [LICENSE](./LICENSE) for details.

## Acknowledgments

* Anyone whose code was used
* Friends and family
