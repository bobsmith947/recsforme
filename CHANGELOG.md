# recsforme changelog

## February 22, 2018 - Alpha Release

**Fixed:**

* Queries will perform searches directly in the constructor now, instead of independently in methods, should give a speed boost.
* MovieQuery will now grab the full plot synopsis by default.
* Selection box now reflects that `album` type queries can also include EP's and Singles primarily.

**Added:**

* Artist and Album servlets now print out basic info.
* Better javadoc comments, including `package-info`.

**Removed:**

* `GenericQuery` because it was kinda redundant.

The release is not meant for production. Notable features missing include:

* JSP header/footer
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
