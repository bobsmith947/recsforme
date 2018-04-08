# recsforme changelog

## April 8, 2018 - Beta Release

It's been almost a month since the last release. This is the first beta, although the "recs" part of recsforme has been cut from the original roadmap in the interest of time. Pretty much everything else has been improved upon though. Here are some highlights:

* Bootstrap styling so that you don't have to deal with my terrible CSS
* Added some icons because why not
* Bundled everything except jQuery. There will be a warning if the jQuery script cannot be loaded
* Removed polyfills to save on space in the bundle. These may come back though
* Added lots of new pages to allow for users to register and whatnot
* Added the `app` package to deal with stuff like password encryption and user accounts

## March 9, 2018 - Alpha Release

**Fixed:**

* `AlbumInfo` will search in `release` if it cannot resolve a `release-group`
* `AlbumInfo` and `ArtistInfo` now escape all dangerous characters
* Switched the server to Apache Tomcat. This is a more lightweight solution (only the basic features of the Java EE spec are needed) that also allows for database connectivity due to difficulties with GlassFish
* JSTL core tags are used instead of JSP scriptlets. Hopefully the source for JSP's is more readable now

**Added:**

* Vote data for groups is sent to the server using AJAX, and stored using `localStorage`. jQuery and Knockout were added as JavaScript dependencies
* Refactored most of the client-side script with jQuery code

**Removed:**

* Functionality for `AlbumQuery` to grab information on release editions. It wasn't really needed and could make the page load much slower if I didn't add a separate parameter, but I didn't really like that anyway, so I just got rid of it entirely

**Missing:**

* Recommendation engine. I added a package and an interface, but that's about it. No real functionality

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
