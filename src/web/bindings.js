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
import moment from "moment/min/moment.min.js";

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
          return date.fromNow(true);
        else 
          return "unknown";
      },
      completed: function() {
        return this.accepted() && 
                this.notRobot() && 
                this.pw() === this.pwc() && 
                $("#valid-name").length !== 0;
      },
      sendInfo: function() {
        if ($("#info-form")[0].checkValidity()) {
          $("#subres").empty();
          $.post("register.jsp", 
            $("#info-form").serialize(), 
            response => $("#subres").append(response));
          $("button[form=info-form]").prop("disabled", true);
        }
      },
      nameCheck: function() {
        $("#checkres").empty();
        if (this.uname() !== "") {
          $.get("register.jsp",
           {name: this.uname()},
           response => $("#checkres").append(response));
        }
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
        $("#subres").empty();
        const reset = $(form).serializeArray();
        this.resetForm(false);
        $.post("register.jsp",
          {
            email: reset[0].value,
            pass: reset[1].value,
            name: this.name(),
            action: "reset"
          },
          response => $("#subres").append(response));
      }
    };
    ko.applyBindings(logInModel);
  }
  if (location.pathname.includes("Info")) {
    let name = $("#name").text();
    let type = $("#type").text();
    let id = location.search.substring(4);
    let json = generateItem(name, id, type);
    let vote = checkVote(json);
    let voteModel = {
      status: ko.observable(vote),
      selected: ko.observable(false),
      voted: ko.observable(vote !== null),
      sendVote: function(form) {
        vote = $(form).serializeArray()[0].value;
        let xhr = $.post("group.jsp", 
          {
            status: vote,
            name: name,
            id: id,
            type: type,
            action: "add"
          });
        xhr.fail(() => {
          switch (vote) {
            case "like":
              $("#vote-form").append(`<h5 class="text-danger">This group already exists on your dislikes list.</h5>`);
              break;
            case "dislike":
              $("#vote-form").append(`<h5 class="text-danger">This group already exists on your likes list.</h5>`);
              break;
          }
          console.log("This item has already been added.");
          this.selected(false);
        });
        xhr.done(() => {
          this.status(vote);
          this.voted(true);
        });
      },
      undoVote: function() {
        $.post("group.jsp", 
          {
            status: this.status(),
            name: name,
            id: id,
            type: type,
            action: "remove"
          });
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

function generateItem(name, id, type) {
  return JSON.stringify({
    name: name,
    id: id,
    type: type
  });
}

function checkVote(json) {
  let group = JSON.parse(json);
  //TODO maybe find another solution
  //synchronous request is deprecated
  let xhr = $.ajax({
          async: false,
          url: "group.jsp",
          data: {
            name: group.name,
            id: group.id,
            type: group.type,
            action: "check"
          }
        });
  return xhr.getResponseHeader("Item-Contained");
}
