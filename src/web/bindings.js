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

import ko from "knockout/build/output/knockout-latest.js";

$(() => {
  try {
    if (location.pathname.includes("signup.jsp")) {
      let signUpModel = {
        uname: ko.observable(""),
        pw: ko.observable(""),
        pwc: ko.observable(""),
        email: ko.observable(""),
        sex: ko.observable(""),
        accepted: ko.observable(false),
        notRobot: ko.observable(false),
        dob: ko.observable(""),
        age: function() {
          const date = moment(this.dob());
          if (date.isValid()) return date.fromNow();
          else return "unknown";
        },
        completed: function() {
          return (this.accepted() && this.notRobot()) 
                  && (this.uname() !== "") 
                  && (this.pw() !== "" && this.pw() === this.pwc());
        },
        sendInfo: function() {
          $("#subres").empty();
          $.post("register.jsp", 
                $("#info-form").serialize(), 
                response => $("#subres").append(response));
          $("button[form=info-form]").prop("disabled", true);
        },
        nameCheck: function() {
          $("#checkres").empty();
          if (this.uname() !== "") {
            $.get("register.jsp",
                 {name: this.uname()},
                 response => $("#checkres").append(response));
          } else $("#checkres").append("You have not entered a username to check.");
        }
      };
      ko.applyBindings(signUpModel);
    }
    if (location.pathname.includes("Info")) {
      const group = $("#name").text();
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
          $.post("GroupVote", 
                {name: voteData.get("name"), id: voteData.get("id"), like: voteData.get("like")}, 
                response => $("#vote-div").append(response));
          localStorage.setItem(group, this.status());
        },
        undoVote: function() {
          this.hasVoted(false);
          this.hasSelected(false);
          localStorage.removeItem(group);
          $("#response").remove();
        }
      };
      ko.applyBindings(voteModel);
    }
  } catch (ex) {
    console.log(ex);
    console.log("Knockout bindings not applied.");
  }
});

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