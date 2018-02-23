# recsforme changelog

## February 22, 2018 - Alpha Release

This alpha contains added functionality for Artist and Album servlets. Song functionality and the GenericQuery interface were removed, however they may be added back in for future releases (see `java-test`).

Some optimizations for MovieQuery have been made, and it will only grab full info when needed. It also grabs the full plot synopsis by default.

The `album` selection in the JSP form has been updated to reflect that it can also contain EP's and Singles primarily.

javadoc comments have been improved, including `package-info`.

The release is not meant for production. Notable features missing include:

* JSP header/footer
* Further query optimizations
* JUnit tests
* Recommendation engine
* Database connectivity

## February 13, 2018 - Alpha Release

The first release of recsforme! This release is not meant for production. Notable features missing include:

* JSP header/footer
* Functionality for music servlets
* Query optimizations
* JUnit tests
* Recommendation engine
* Database connectivity
