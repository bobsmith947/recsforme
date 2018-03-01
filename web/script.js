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

import "babel-polyfill";
import moment from "moment";

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
  let d = null;
  for (i = 0; i < elems.length; i++) {
    let d = moment(elems[i].innerHTML);
    if (d.isValid()) elems[i].innerHTML = d.format("LL");
    else elems[i].innerHTML = "Unknown date";
    elems[i].style.display = "inline";
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
