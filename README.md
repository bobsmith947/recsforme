# recsforme

[![Build Status](https://travis-ci.org/bobsmith947/recsforme.svg?branch=master)](https://travis-ci.org/bobsmith947/recsforme)

A media recommendation web app.

It allows for users to search for artists and albums (using data from MusicBrainz), and add them to their like and dislike lists. From there, a recommendation algorithm is able to suggest new artists and albums for the user, based on what similar users have liked.

The backend is composed of Java classes, beans, and servlets. The Javadoc for these can be viewed [here](https://bobsmith947.github.io/javadoc/). There are also a number of JavaServer Pages (JSP) as well as a SQL database. Bootstrap is used for styling the pages, and Knockout is used for managing states.

## Getting Started

Want to get recsforme set up on your local machine for development and testing purposes? Great! This is how to do so:

### Prerequisites

* JDK (at least Java 8)
* [Apache Tomcat 8.5](https://tomcat.apache.org/download-80.cgi)
* PostgreSQL 10+
* [Apache Ant and Apache Ivy](https://ant.apache.org/)
* npm (comes with [Node.js](https://nodejs.org/en/download/))
* ~~[An OMDb API key](https://www.omdbapi.com/) (set it as an environment variable named *OMDB_KEY*)~~

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

#### Setting up the database

To set up a local PostgreSQL database, make sure the `PGDATA` and `PGDATABASE` environment variables are set, and then run the `initdb` and `createdb` commands. The `pg_ctl` command can be used to manage the database server, and the `psql` command can be used to interact with the database.

The scripts provided are written for use with PostgreSQL. It may be possible to use with other SQL implementations with some modifications. You need to perform these steps in order to set up the database.

1. Execute `MusicTables.sql`
2. [Copy the MusicBrainz data into the tables](https://musicbrainz.org/doc/MusicBrainz_Database/Download) using `CopyDump.sql`
3. Execute `UserTables.sql`
4. Execute `InsertGroups.sql`

### Installing

    cd *directory you want to install recsforme in*
    git clone https://github.com/bobsmith947/recsforme.git
    cd recsforme
    npm install
    ant resolve

### Running

Start the server:

    ant start

Build and deploy the application:    

    ant run
    
Redeploy the application (when you make changes):

    ant redeploy

Stop the server:

    ant stop
    
Run unit tests:

    ant test

Cleanup build:

    ant clean

Prepare the local database:

    sudo -E ant mkdb

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
