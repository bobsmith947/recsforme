/*
 * Copyright 2018 Lucas Kitaev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//import "babel-polyfill";
import moment from "moment/min/moment.min.js";

document.body.onload = () => {
  //set external links to open in a new tab/window
  let elems = document.links, i = undefined;
  for (i = 0; i < elems.length; i++) {
    if (!elems[i].href.includes(`://${document.domain}`)) elems[i].target = "_blank";
  }
  //add listener to expand images on click
  elems = document.images;
  for (i = 0; i < elems.length; i++) {
    if (screen.width > 1024 && elems[i].className === "exp") {
      elems[i].addEventListener("click", expandImg);
      elems[i].title = "Click to expand.";
      elems[i].style.width = "15%";
    }
  }
  //format dates
  elems = document.getElementsByClassName("date");
  for (i = 0; i < elems.length; i++) {
    const s = elems[i].innerHTML;
    const d = moment(s, "YYYY-MM-DD", true);
    if (d.isValid()) elems[i].innerHTML = d.format("LL");
    else if (s === "null") elems[i].innerHTML = "Unknown date";
    else elems[i].innerHTML = s;
  }
}

function expandImg(ev) {
  const elem = ev.target, w = elem.naturalWidth;
  switch(elem.style.width) {
    case "15%" :
      if (w > 0.9 * screen.width) elem.style.width = "90%";
      else elem.style.width = `${w}px`;
      elem.title = "Click to shrink."
      break;
    default :
      elem.style.width = "15%";
      elem.title = "Click to expand.";
      break;
  }
}

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/includes
if (!String.prototype.includes) {
  String.prototype.includes = function(search, start) {
    'use strict';
    if (typeof start !== 'number') {
      start = 0;
    }

    if (start + search.length > this.length) {
      return false;
    } else {
      return this.indexOf(search, start) !== -1;
    }
  };
}

// https://tc39.github.io/ecma262/#sec-array.prototype.includes
if (!Array.prototype.includes) {
  Object.defineProperty(Array.prototype, 'includes', {
    value: function(searchElement, fromIndex) {

      if (this == null) {
        throw new TypeError('"this" is null or not defined');
      }

      // 1. Let O be ? ToObject(this value).
      var o = Object(this);

      // 2. Let len be ? ToLength(? Get(O, "length")).
      var len = o.length >>> 0;

      // 3. If len is 0, return false.
      if (len === 0) {
        return false;
      }

      // 4. Let n be ? ToInteger(fromIndex).
      //    (If fromIndex is undefined, this step produces the value 0.)
      var n = fromIndex | 0;

      // 5. If n ≥ 0, then
      //  a. Let k be n.
      // 6. Else n < 0,
      //  a. Let k be len + n.
      //  b. If k < 0, let k be 0.
      var k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

      function sameValueZero(x, y) {
        return x === y || (typeof x === 'number' && typeof y === 'number' && isNaN(x) && isNaN(y));
      }

      // 7. Repeat, while k < len
      while (k < len) {
        // a. Let elementK be the result of ? Get(O, ! ToString(k)).
        // b. If SameValueZero(searchElement, elementK) is true, return true.
        if (sameValueZero(o[k], searchElement)) {
          return true;
        }
        // c. Increase k by 1.
        k++;
      }

      // 8. Return false
      return false;
    }
  });
}
