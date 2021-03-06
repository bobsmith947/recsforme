# recsforme changelog

## September 26, 2018 - Release

Changes to reflect the domain no longer being used. No changes to the Javadoc.

## July 30, 2018 - Release

Quick patch to make the default sorting option more clear. No changes to the Javadoc.

## July 24, 2018 - Release

Probably the last release for the foreseeable future. There are a few things that could still be implemented, but I think it's best to put the project on hold now.

**Fixed:**

* Error handling is now less spaghetti (could still use dedicated logging)
* Use more static imports when it makes sense
* CSS and noscript moved to header
* Parameter naming should be more consistent
* Artists will show their sort name (helpful with foreign artists). This required existing artists to be cleared from the database

**Added:**

* Enum for servlet path contexts and query delegation
* Error page stuff
* Album and list ordering/sorting/filtering
* Created a SQL template so the database can easily be recreated
* Login/logout/signup links in the navbar
* Local lists can be merged
* Recommendations are now displayed on the user page

**Removed:**

* "Various Artists" contributions are no longer shown on artist pages

## June 23, 2018 - Release

It's been two months since the last release, and the recommendation engine has finally been implemented. Extensive testing has not been done, but I'm going to count this as a stable release.

**Fixed:**

* Artist vote moved to top of page so there's no need to scroll
* Whitespace bug in searches (sometimes returned nothing)
* Use `c:out` when printing the query to the page (in case it had quotes)
* Form validation on signup

**Added:**

* Recommendation engine
* Login modal on user page
* AJAX check for groups
* `ListData` now implements `Comparable` because why not

**Removed:**

* Removed usage of `localStorage`
* Unnecessary character replacement

## April 21, 2018 - Beta Release

Some quick bug fixes:

* Fixed being unable to remove groups
* Fixed bean lists not being updated (caused incorrect data to be synced)
* Fixed SQL parameterization

## April 16, 2018 - Beta Release

This is the last planned release. Development may or may not continue in June and July of 2018.

**Fixed:**

* User ages are now formatted with `moment#fromNow(true)` rather than `moment#fromNow()` so that it just has the time since and not the "ago" part because that made it kinda weird
* Email reset messages now won't append on top of each other

**Added:**

* Groups can now be removed from the database list
* Lists will be checked for duplicates even though it should technically be impossible to add duplicates
* HTML5 form validation for usernames so that they can actually only be alphanumeric characters
* SQL parameters are now used for user-submitted info
* Put back in a polyfill for `String.prototype.includes()` since that's pretty much the only thing that needed a polyfill
* Made a bunch of unit tests so hopefully the core Java code is more reliable now

**Removed:**

* `FormData` is no longer used for some AJAX data. Edge doesn't support it and it isn't any easier to use than making an object. jQuery serialization is still used for some cases where extra data doesn't need to be added in

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

* Recommendation engine

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

The first release of recsforme. Notable features missing include:

* JSP header/footer
* Functionality for music servlets
* Query optimizations
* JUnit tests
* Recommendation engine
* Database connectivity
