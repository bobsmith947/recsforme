# recsforme changelog

## February 28, 2018 - Alpha Release

**Fixed:**

* Info is grabbed by ID rather than name
* `AlbumInfo` prints out the correct type and date

**Added:**

* `QueryBean` supplies the correct ID and servlet context automatically
* `AlbumInfo` prints out the tracklist and editions
* `ArtistInfo` links to relevant release groups
* Apache Ivy integration for dependencies
* JSP Fragments for header and footer
* JSP pages for 404 and 500 errors
* Test to ensure name and ID arrays are the same length

**Removed:**

* `MusicQuery` becuase it didn't have much use
* Servlets don't encapsulate their fields

**Missing:**

* Recommendation engine
* Database connectivity

## February 22, 2018 - Alpha Release

**Fixed:**

* Queries will perform searches directly in their constructor
* MovieQuery will grab the full plot synopsis by default
* Selection box reflects that `album` type queries can also include EP's and Singles (primarily)

**Added:**

* `ArtistInfo` and `AlbumInfo` print out basic info
* Better javadoc comments, including `package-info`

**Removed:**

* `GenericQuery` because it was kinda redundant

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
