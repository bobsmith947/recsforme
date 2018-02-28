# recsforme changelog

## February 28, 2018 - Alpha Release

**Fixed:**

* Info is grabbed by ID rather than name

**Added:**

* `QueryBean` is now able to supply the correct ID and context automatically
* Albums now print out the correct type, date and tracklist
* Apache Ivy integration for dependencies
* JSP Fragments for header and footer using property group
* Error pages for 404 and 500
* Test to ensure name and ID arrays are the same length

**Removed:**

* `MusicQuery` becuase it didn't have much use
* Servlets no longer encapsulate their fields

**Missing:**

* Recommendation engine
* Database connectivity

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

**Missing:**

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
