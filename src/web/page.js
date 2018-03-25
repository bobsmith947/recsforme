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

import moment from "moment/min/moment.min.js";

$(() => {
  $("body").addClass("bg-dark text-light");
  $("#removegroup").click(function(ev) {
    if (confirm("Are you sure you want to remove this group?")) {
      localStorage.removeItem($(this).data("name"));
      alert("Group removed.");
      window.open("user.jsp", "_self");
    } else {
      alert("Group not removed.");
      if ($("#info").length > 0) 
        console.log("You already tried removing this!");
      else
        $("main").append("<h5 id='info' class='mt-3'>This group is still in your <span style='text-decoration:underline'>"
                + localStorage.getItem($(this).data("name")) + "</span> list.</h5>");
      $(this).blur();
    }
  });
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
  if (location.pathname.includes("user.jsp")) {
    //add groups
    for (var i = 0; i < localStorage.length; i++) {
      const group = localStorage.key(i);
      switch (localStorage.getItem(group)) {
        case "like":
          $("#likes").append(`<a href="group.jsp?name=${encodeURIComponent(group)}" class="list-group-item list-group-item-action">${group}</a>`);
          break;
        case "dislike":
          $("#dislikes").append(`<a href="group.jsp?name=${encodeURIComponent(group)}" class="list-group-item list-group-item-action">${group}</a>`);
          break;
        default:
          console.log("Group not found.");
          break;
      }
    }
    //notify if nothing could be added
    if (localStorage.length === 0) {
      $("#listreset").prop("disabled", true);
      $("#list").empty();
      $("#list").append("<h6>Your list is empty!</h6>");
      $("#list").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
      $("#resetprompt").addClass("text-muted");
    } else if ($("#likes").children().length === 0) {
      $("#likes").append("<h6>You haven't added any likes!</h6>");
      $("#likes").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
    } else if ($("#dislikes").children().length === 0) {
      $("#dislikes").append("<h6>You haven't added any dislikes!</h6>");
      $("#dislikes").append("<h6><a href='search.jsp'>Click here to search for things to add.</a></h6>");
    }
    //clear the list
    $("#listreset").click(ev => {
      if (confirm("Are you sure you want to clear your list?")) {
        localStorage.clear();
        alert("List cleared.");
        location.reload();
      } else {
        alert("List not cleared.");
        ev.target.blur();
      }
    });
  }
});
/*
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
*/
