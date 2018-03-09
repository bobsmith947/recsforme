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
import $ from "jquery";
import moment from "moment/min/moment.min.js";
import ko from "knockout";

$(() => {
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
    if (date.isValid()) $(cur).text(date.format("LL"));
    else if (str === "null") $(cur).text("Unknown date");
    else $(cur).text(str);
  });
  //knockout bindings for group page
  try {
    const group = document.title.substring(document.title.indexOf(": ")+2);
    const id = location.search.substring(4);
    let vote;
    function checkVote() {
      try {
        const val = localStorage.getItem(group);
        vote = val;
        return val !== null;
      } catch (ex) {
        console.log(ex);
        console.log("Not found in list.");
        return false;
      }
    }
    //$("#vote-form").submit(e => e.preventDefault());
    let voteModel = {
      hasSelected: ko.observable(false),
      hasVoted: ko.observable(checkVote()),
      name: ko.observable(group),
      status: ko.observable(vote),
      sendVote: function(form) {
        let voteData = new FormData(form);
        voteData.append("name", group);
        voteData.append("id", id);
        this.status(isLike(voteData.get("like")));
        this.hasVoted(true);
        $.post("GroupVote", $(form).serialize(), (response, message) => {
          if (message === "success") $("#response").append(response);
          else alert("Failed to send data to server.");
        });
        localStorage.setItem(group, this.status());
      },
      undoVote: function() {
        this.hasVoted(false);
        this.hasSelected(false);
        localStorage.removeItem(group);
        $("#response").empty();
      }
    };
    ko.applyBindings(voteModel);
  } catch (ex) {
    console.log(ex);
    console.log("Knockout bindings not applied.");
  }
  //user page populate
  if (location.pathname.includes("user.jsp")) {
    for (var i = 0; i < localStorage.length; i++) {
      const group = localStorage.key(i);
      switch (localStorage.getItem(group)) {
        case "like":
          $("#likes").append(`<li>${group}</li>`);
          break;
        case "dislike":
          $("#dislikes").append(`<li>${group}</li>`);
          break;
        default:
          console.log("Group not found.");
          break;
      }
    }
  }
});

function expandImg(ev) {
  const elem = ev.target, w = elem.naturalWidth;
  switch (elem.style.width) {
    case "15%":
      if (w > 0.9 * screen.width) elem.style.width = "90%";
      else elem.style.width = `${w}px`;
      elem.title = "Click to shrink."
      break;
    default:
      elem.style.width = "15%";
      elem.title = "Click to expand.";
      break;
  }
}

function isLike(str) {
  switch (str) {
    case "true":
      return "like";
      break;
    case "false":
      return "dislike";
      break;
    default:
      return "either like or dislike";
      break;
  }
}
