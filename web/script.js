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
if (!String.prototype.includes) {
  String.prototype.includes = function(search, start) {
    if (typeof start !== "number") {
      start = 0;
    }
    
    if (start + search.length > this.length) {
      return false;
    } else {
      return this.indexOf(search, start) !== -1;
    }
  };
}
document.body.onload = () => {
  let elems = document.links, len = elems.length, i = undefined;
  //set link class based on href and add appropriate target
  for (i = 0; i < len; i++) {
    if (elems[i].href.includes(`://${document.domain}`)) elems[i].className = "int";
    else {
      elems[i].className = "ext";
      elems[i].target = "_blank";
    }
  }
  //add listener to expand images on click
  elems = document.images;
  len = elems.length;
  for (i = 0; i < len; i++) {
    if (screen.width > 1024 && elems[i].className === "exp") {
      elems[i].addEventListener("click", expandImg);
      elems[i].title = "Click to expand.";
      elems[i].style.width = "15%";
    }
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