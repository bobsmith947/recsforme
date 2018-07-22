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
window.Util = require("exports-loader?Util!bootstrap/js/dist/util");
import "bootstrap/js/dist/alert";
import "bootstrap/js/dist/collapse";
import "bootstrap/js/dist/dropdown";
import "bootstrap/js/dist/modal";
import "@fortawesome/fontawesome-free-webfonts/css/fontawesome.css";
import "@fortawesome/fontawesome-free-webfonts/css/fa-solid.css";
import "@fortawesome/fontawesome-free-webfonts/css/fa-brands.css";

$(() => {
  //format dates/times
  $(".date").each(function () {
    const str = $(this).text();
    const date = moment(str, "YYYY-MM-DD", true);
    if (date.isValid())
      $(this).text(date.format("LL"));
    else if (str === "null")
      $(this).text("Unknown date");
    else
      $(this).text(str);
  });
  $("#timeout").text((index, text) => {
    if (index !== 0)
      return "";
    else
      return `${moment(text, "X").format("m")} minutes`;
  });
  //reverse the ordering of elements in a list
  $(".orderer").click(ev => {
    ev.preventDefault();
    const list = $($(ev.target).attr("data-target"));
    const items = list.children();
    list.empty();
    list.append(items.toArray().reverse());
  });
  //error pages
  $("#escape").click(ev => {
    ev.preventDefault();
    history.back();
  });
  if (location.pathname.includes("user.jsp")) {
    $("a[href='user.jsp'] + span[data-toggle=dropdown]").remove();
    if (location.href.includes("#login"))
      $("#login").modal("show");
    $("#recgen").click(() => {
      $.get("group.jsp", 
      {action: "recommend"}, 
      response => $("#recslist").append($(response).filter(".res")));
    });
    $(".filter").click(ev => {
      ev.preventDefault();
      const target = $(ev.target);
      const filter = target.attr("data-filter");
      const list = $(target.attr("data-target"));
      if (!filter)
        list.children().show();
      else {
        list.children().each(function () {
          if ($(this).attr("href").includes(filter))
            $(this).show();
          else if (!$(this).attr("href").includes(filter))
            $(this).hide();
          else
            return;
        });
      }
    });
    //warn if the list(s) are empty
    const nolikes = $("#likes").children().length === 0;
    const nodislikes = $("#dislikes").children().length === 0;
    if (nolikes && nodislikes) {
      $(".listreset").prop("disabled", true);
      $("#list").append(`<h6 id="warn">Your lists are empty!</h6>`);
      $("#resetprompt").addClass("text-muted");
    } else if (nolikes) {
      $(".listreset[data-list=like]").prop("disabled", true);
      $("#likes").append(`<h6 id="warn">You haven't added any likes!</h6>`);
    } else if (nodislikes) {
      $(".listreset[data-list=dislike]").prop("disabled", true);
      $("#dislikes").append(`<h6 id="warn">You haven't added any dislikes!</h6>`);
    }
    $("#warn").addClass("text-warning");
    if (nolikes || nodislikes) {
      $(".listreset[data-list=both]").prop("disabled", true);
      $("#warn").after(`<h6><a href="search.jsp">Click here to search for things to add.</a></h6>`);
    }
    //clear the list(s)
    $(".listreset").click(ev => {
      const list = $(ev.target).attr("data-list");
      if (confirm("Are you sure you want to clear your list(s)?")) {
        $.get("group.jsp", {
            action: "reset",
            list: list
          });
        alert("List(s) cleared.");
        location.reload();
      } else {
        alert("List(s) not cleared.");
        ev.target.blur();
      }
    });
  } else {
    $.get("login.jsp", {action: "check"}, response => {
      const links = $(response).find(".profilelink");
      $(links).each(function () {
        $(this).attr("class", "dropdown-item");
        $(this).removeAttr("data-toggle data-target");
      });
      $("#profilelinks").append(links);
    });
  }
});

//polyfill just in case
if (!String.prototype.includes) {
  String.prototype.includes = function (search, start) {
    if (typeof start !== 'number')
      start = 0;
    if (start + search.length > this.length)
      return false;
    else
      return this.indexOf(search, start) !== -1;
  };
}
