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

var title = document.title;
$("input[name=name]").val(title.substring(title.indexOf(": ")+2, title.indexOf(" - ")));
$("input[name=type]").val(title.substring(title.indexOf("- ")+2));
$("input[name=id]").val(location.search.substring(4));
var viewModel = {
  confirmation: ko.observable(false),
  type: ko.observable(""),
  name: ko.observable(""),
  status: ko.observable(""),
  //TODO add form validation
  sendVote: function (form) {
    var voteData = new FormData(form);
    this.type(voteData.get("type"));
    this.name(voteData.get("name"));
    this.status(isLike(voteData.get("like")));
    this.confirmation(true);
    $.post("GroupVote", $(form).serialize(), function (data, status) {
      if (status === "success") {
        $("#response").append(data);
      } else {
        $("#response").append("Failed to send data to server.");
      }
    });
  }
};
ko.applyBindings(viewModel);
//TODO check if this works
if (!FormData.prototype.get) {
  FormData.prototype.get = function(key) {
    var arr = $("#vote-form").serializeArray();
    var red = arr.reduce(function (obj, item) {
      obj[item.name] = item.value;
      return obj;
    });
    return red[key];
  };
}
function isLike(str) {
  if (str === "true")
    return "like";
  else if (str === "false")
    return "dislike";
  else
    return "either like or dislike";
}
