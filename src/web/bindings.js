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
          if (date.isValid())
            return date.fromNow();
          else return "unknown";
        },
        completed: function() {
          return (this.accepted() && this.notRobot()) 
                  && (this.uname() !== "") 
                  && (this.pw().length >= 8 && this.pw() === this.pwc());
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
    if (location.pathname.includes("login.jsp")) {
      let logInModel = {
        name: ko.observable(""),
        resetForm: ko.observable(false),
        email: ko.observable(""),
        pass: ko.observable(""),
        passCheck: ko.observable(""),
        requestReset: function(form) {
          const requestData = new FormData(form);
          requestData.append("action", "reset");
          requestData.append("name", this.name());
          this.resetForm(false);
          $.post("auth.jsp",
                encodeFormData(requestData),
                response => $("main").append(response));
        }
      };
      ko.applyBindings(logInModel);
    }
    if (location.pathname.includes("Info")) {
      const group = $("#name").text();
      const type = $("#type").text();
      const id = location.search.substring(4);
      let voteModel = {
        selected: ko.observable(false),
        voted: ko.observable(checkGroup(group)),
        sendVote: function(form) {
          const voteData = new FormData(form);
          voteData.append("name", group);
          voteData.append("type", type);
          voteData.append("id", id);
          this.voted(true);
          $.post("group.jsp", 
                encodeFormData(voteData));
          localStorage.setItem(group, this.status());
        },
        undoVote: function() {
          try {
            localStorage.removeItem(group);
          } catch (ex) {
            console.log(ex);
            console.log("Group not found.");
          } finally {
            $.get("group.jsp", 
              {
                action: "remove",
                name: group
              });
          }
          this.voted(false);
          this.selected(false);
        }
      };
      ko.applyBindings(voteModel);
    }
  } catch (ex) {
    console.log(ex);
    console.log("Knockout bindings not applied.");
  }
});

/*function isLike(str) {
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
}*/

function checkGroup(group) {
  if (localStorage.getItem(group) !== null)
    return true;
  else {
    let res;
    $.get("group.jsp",
          {
            action: "check",
            name: group
          },
          response => res = $(response).text());
    switch (res) {
      case "exists":
        return true;
        break;
      case "does not exist":
        return false;
        break;
      default:
        console.log("Something went wrong.");
        return false;
        break;
    }
  }
}

//https://stackoverflow.com/questions/7542586/new-formdata-application-x-www-form-urlencoded
function encodeFormData(fd) {
  let params = new URLSearchParams();
  for (let pair of fd.entries())
    params.append(pair[0], pair[1]);
  return params.toString();
}