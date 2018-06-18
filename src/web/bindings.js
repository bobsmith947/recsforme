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
    let changed = false;
    let signUpModel = {
      uname: ko.observable(""),
      pw: ko.observable(""),
      pwc: ko.observable(""),
      email: ko.observable(""),
      sex: ko.observable(""),
      accepted: ko.observable(false),
      dob: ko.observable(""),
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
          $("#info-form").serialize(), 
          response => $("#subres").append(response));
        $("button[form=info-form]").prop("disabled", true);
      },
      nameCheck: function () {
        if ($("#uname")[0].checkValidity() && changed) {
          $("#nameres").empty();
          changed = false;
          $.get("register.jsp", {name: this.uname()})
                  .done((response) => {
                    $("#nameres").append(response);
                    $("#valid-name").length === 1 ? $("#uname")[0].setCustomValidity("") :
                            $("#uname")[0].setCustomValidity("Username is already taken.");
                  });
        }
      },
      emailCheck: function () {
        $("#emailres").empty();
        if (this.uname() === this.email().substring(0, this.email().indexOf("@"))) {
          $("#emailres").text("The email you use cannot be the same as your username.");
          $("#email")[0].setCustomValidity("Email cannot match username.");
        } else
          $("#email")[0].setCustomValidity("");
      },
      passCheck: function () {
        $("#passres").empty();
        if (this.pw() !== this.pwc()) {
          $("#passres").text("The passwords you entered do not match.");
          $("#pw")[0].setCustomValidity("Passwords aren't the same.");
        } else
          $("#pw")[0].setCustomValidity("");
      },
      valid: function () {
        if (this.accepted())
          return $("#info-form")[0].checkValidity();
      }
    };
    ko.applyBindings(signUpModel);
    $("#uname").change(() => {
      $("#uname")[0].setCustomValidity("");
      changed = true;
    });
    $("#info-form").find("input").change(() => signUpModel.accepted(false));
  }
  if (location.pathname.includes("user.jsp")) {
    let logInModel = {
      name: ko.observable(""),
      resetForm: ko.observable(false),
      email: ko.observable(""),
      pass: ko.observable(""),
      passCheck: ko.observable(""),
      requestReset: function (form) {
        $("#subres").empty();
        const reset = $(form).serializeArray();
        $.post("register.jsp", {
            email: reset[0].value,
            pass: reset[1].value,
            name: this.name(),
            action: "reset"
          })
                .done((response) => {
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
