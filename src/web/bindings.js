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
        $("#subres").empty();
        const reset = $(form).serializeArray();
        this.resetForm(false);
        $.post("auth.jsp",
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
    const name = $("#name").text();
    const type = $("#type").text();
    const id = location.search.substring(4);
    const json = generateItem(name, id, type);
    const vote = localStorage.getItem(json);
    let voteModel = {
      selected: ko.observable(false),
      voted: ko.observable(vote !== null),
      status: ko.observable(vote),
      sendVote: function(form) {
        const vote = $(form).serializeArray();
        this.voted(true);
        this.status(vote[0].value);
        $.post("group.jsp", 
          {
            status: vote[0].value,
            name: name,
            id: id,
            type: type,
            action: "add"
          });
        localStorage.setItem(json, this.status());
      },
      undoVote: function() {
        localStorage.removeItem(json);
        $.post("group.jsp", 
          {
            action: "remove",
            name: name,
            id: id,
            type: type,
            status: this.status()
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
  name = name.replace("'", "''");
  return JSON.stringify({
    name: name,
    id: id,
    type: type
  });
}