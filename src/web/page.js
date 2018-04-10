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
import "./style.css";
window.Util = require("exports-loader?Util!bootstrap/js/dist/util");
import "bootstrap/js/dist/alert";
import "bootstrap/js/dist/button";
import "bootstrap/js/dist/collapse";
import "@fortawesome/fontawesome-free-webfonts/css/fontawesome.css";
import "@fortawesome/fontawesome-free-webfonts/css/fa-solid.css"
import "@fortawesome/fontawesome-free-webfonts/css/fa-brands.css";

$(() => {
  $("#warning").css("display", "none");
  $("body").addClass("bg-dark text-light");
  //add listener to expand images on click
  /*if (screen.width > 1024) {
    $(".exp").each((ind, cur) => {
      $(cur).click(expandImg);
      $(cur).attr("title", "Click to expand.");
      $(cur).css("width", "15%");
    });
  }*/
  //format dates
  $(".date").each((ind, cur) => {
    const str = $(cur).text();
    const date = moment(str, "YYYY-MM-DD", true);
    if (date.isValid())
      $(cur).text(date.format("LL"));
    else if (str === "null")
      $(cur).text("Unknown date");
    else
      $(cur).text(str);
  });
  if (location.pathname.includes("user.jsp")) {
    //add groups
    for (let i = 0; i < localStorage.length; i++) {
      const item = localStorage.key(i);
      const group = JSON.parse(item);
      const context = generateContext(group.type);
      switch (localStorage.getItem(item)) {
        case "like":
          $("#likes").append(`<a href="${context}${group.id}" class="list-group-item list-group-item-action">${group.name}</a>`);
          break;
        case "dislike":
          $("#dislikes").append(`<a href="${context}${group.id}" class="list-group-item list-group-item-action">${group.name}</a>`);
          break;
        default:
          console.log("Group not found.");
          break;
      }
    }
    //notify if nothing could be added
    const nolikes = $("#likes").children().length === 0;
    const nodislikes = $("#dislikes").children().length === 0;
    if (nolikes && nodislikes) {
      $(".listreset").prop("disabled", true);
      $("#list").empty();
      $("#list").append("<h6>Your lists are empty!</h6>");
      $("#list").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
      $("#resetprompt").addClass("text-muted");
    } else if (nolikes) {
      $(".listreset[data-list=likes]").prop("disabled", true);
      $("#likes").append("<h6>You haven't added any likes!</h6>");
      $("#likes").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
    } else if (nodislikes) {
      $(".listreset[data-list=dislikes]").prop("disabled", true);
      $("#dislikes").append("<h6>You haven't added any dislikes!</h6>");
      $("#dislikes").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
    }
    //clear the list
    $(".listreset").click(ev => {
      if (confirm("Are you sure you want to clear your list(s)?")) {
        const action = $(ev).attr("data-list");
        if (action === "both") {
          localStorage.clear();
          console.log("localStorage cleared.");
        } else {
          for (let i = 0; i < localStorage.length; i++) {
            const item = localStorage.key(i);
            if (localStorage.getItem(item) === action)
              localStorage.removeItem(item);
          }
        }
        $.get("group.jsp", 
          {
            action: "reset",
            list: $(ev).attr("data-list")
          });
        alert("List(s) cleared.");
        location.reload();
      } else {
        alert("List(s) not cleared.");
        ev.target.blur();
      }
    });
  }
});

/*function expandImg(ev) {
  const elem = ev.target;
  const w = elem.naturalWidth;
  switch (elem.style.width) {
    case "15%":
      if (w > 0.9 * screen.width)
        elem.style.width = "90%";
      else
        elem.style.width = `${w}px`;
      elem.title = "Click to shrink."
      break;
    default:
      elem.style.width = "15%";
      elem.title = "Click to expand.";
      break;
  }
}*/

function generateContext(type) {
  const movie = "MovieInfo?id=";
  const artist = "ArtistInfo?id=";
  const album = "AlbumInfo?id=";
  switch (type.toLowerCase()) {
    case "movie":
      return movie;
      break;
    case "series":
      return movie;
      break;
    case "game":
      return movie;
      break;
    case "person":
      return artist;
      break;
    case "group":
      return artist;
      break;
    case "orchestra":
      return artist;
      break;
    case "choir":
      return artist;
      break;
    case "character":
      return artist;
      break;
    case "album":
      return album;
      break;
    case "single":
      return album;
      break;
    case "ep":
      return album;
      break;
    case "broadcast":
      return album;
      break;
    case "compilation":
      return album;
      break;
    case "soundtrack":
      return album;
      break;
    case "spokenword":
      return album;
      break;
    case "interview":
      return album;
      break;
    case "audiobook":
      return album;
      break;
    case "live":
      return album;
      break;
    case "remix":
      return album;
      break;
    case "dj-mix":
      return album;
      break;
    case "mixtape/street":
      return album;
      break;
    default:
      return "search.jsp?query=";
      break;
  }
}

//polyfill just in case
if (!String.prototype.includes) {
  String.prototype.includes = function(search, start) {
    if (typeof start !== 'number')
      start = 0;
    if (start + search.length > this.length)
      return false;
    else
      return this.indexOf(search, start) !== -1;
  };
}
