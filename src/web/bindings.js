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
    let path = location.pathname;
  if (path.includes("signup.jsp")) {
    let changed = false;
    const signUpModel = {
      name: ko.observable(""),
      pass: ko.observable(""),
      check: ko.observable(""),
      email: ko.observable(""),
      dob: ko.observable(""),
      sex: ko.observable(""),
      accepted: ko.observable(false),
      age: function () {
        const date = moment(this.dob());
        if (date.isValid())
          return date.fromNow(true); //without the "ago" part
        else 
          return "unknown";
      },
      sendInfo: function () {
        $("#subres").empty();
        $.post("register.jsp", 
          $("#info-form").find("input").filter(function () {
            return $(this).val() !== "";
          }).serialize(), 
          response => $("#subres").append($(response).filter(".res")));
        $("button[form=info-form]").prop("disabled", true);
      },
      nameCheck: function () {
        if ($("#name")[0].checkValidity() && changed) {
          $("#nameres").empty();
          changed = false;
          $.get("register.jsp", {name: this.name()}, response => {
                    $("#nameres").append($(response).filter(".res"));
                    $("#valid-name").length === 1 ? $("#name")[0].setCustomValidity("") :
                            $("#name")[0].setCustomValidity("Username is already taken.");
                  });
        }
      },
      emailCheck: function () {
        $("#emailres").empty();
        if (this.name() === this.email().substring(0, this.email().indexOf("@"))) {
          $("#emailres").text("The email you use cannot be the same as your username.");
          $("#email")[0].setCustomValidity("Email cannot match username.");
        } else
          $("#email")[0].setCustomValidity("");
      },
      passCheck: function () {
        $("#passres").empty();
        if (this.pass() !== this.check()) {
          $("#passres").text("The passwords you entered do not match.");
          $("#pass")[0].setCustomValidity("Passwords aren't the same.");
        } else
          $("#pass")[0].setCustomValidity("");
      },
      valid: function () {
        if (this.accepted())
          return $("#info-form")[0].checkValidity();
      }
    };
    ko.applyBindings(signUpModel);
    $("#name").change(() => {
      $("#name")[0].setCustomValidity("");
      changed = true;
    });
    $("#info-form").find("input").change(() => signUpModel.accepted(false));
  }
  if (path.includes("user.jsp")) {
    const logInModel = {
      name: ko.observable(""),
      email: ko.observable(""),
      pass: ko.observable(""),
      check: ko.observable(""),
      resetForm: ko.observable(false),
      requestReset: function (form) {
        $("#subres").empty();
        const reset = $(form).serializeArray();
        $.post("register.jsp", {
            email: reset[0].value,
            pass: reset[1].value,
            name: this.name(),
            action: "reset"
          })
                .done(response => {
                  $("#pw").val("");
                  this.resetForm(false);
                  $("#subres").append(response);
                })
                .fail(() => {
                  $("#subres").append("Your email and/or username are not correct.");
                  $("#subres").addClass("text-danger");
                  $("#email").focus();
                });
      },
      cancelReset: function () {
        this.name("");
        $("#pw").val("");
        this.resetForm(false);
      },
      beginReset: function () {
        if (this.email())
          return $("#email")[0].checkValidity();
      }
    };
    ko.applyBindings(logInModel);
  }
  if (path.includes("Info")) {
    let name = $("#name").text();
    let type = path.substring(path.lastIndexOf("/") + 1, path.indexOf("Info"));
    let id = location.search.substring(4);
    let json = generateItem(name, id, type);
    let vote = checkVote(json);
    const voteModel = {
      status: ko.observable(vote),
      selected: ko.observable(false),
      voted: ko.observable(vote !== null),
      sendVote: function (form) {
        vote = $(form).serializeArray()[0].value;
        $.post("group.jsp", {
          status: vote,
          name: name,
          id: id,
          type: type,
          action: "add"
        })
                .done(() => {
                  this.status(vote);
                  this.voted(true);
                })
                .fail(() => {
                  switch (vote) {
                    case "like":
                      $("#vote-form").append(`<h5 class="text-danger">This group already exists on your dislikes list.</h5>`);
                      break;
                    case "dislike":
                      $("#vote-form").append(`<h5 class="text-danger">This group already exists on your likes list.</h5>`);
                      break;
                    default:
                      break;
                  }
                  console.log("This item has already been added.");
                  this.selected(false);
                });
      },
      undoVote: function () {
        $.post("group.jsp", {
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
  const group = JSON.parse(json);
  //TODO maybe find another solution
  //synchronous request is deprecated
  const xhr = $.ajax({
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
