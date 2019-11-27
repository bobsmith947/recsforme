<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Sign Up</title>
  <body>
    <main>
      <h2>Account Registration</h2>
      <form id="info-form">
        <div class="form-group">
          <label for="name">Username</label>
          <input data-bind="textInput:name,event:{blur:nameCheck}" type="text" class="form-control" id="name" name="name" placeholder="Enter your desired username" maxlength="50" pattern="\w+" autofocus required>
          <small class="form-text text-muted">Only alphanumeric characters are allowed.</small>
          <div id="nameres"></div>
        </div>
        <div class="form-group">
          <label for="pass">Password</label>
          <input data-bind="textInput:pass" type="password" class="form-control" id="pass" name="pass" placeholder="Enter a secure password" minlength="8" required>
          <small class="form-text text-muted">Your password must be at least 8 characters.</small>
          <input data-bind="enable:pass().length>=8,textInput:check,event:{blur:passCheck}" type="password" class="form-control mt-2" placeholder="Confirm your password" required>
          <div id="passres" class="text-warning mt-2"></div>
        </div>
        <h5>Optional Fields</h5>
        <div class="form-group">
          <label for="email">Email address</label>
          <input data-bind="textInput:email,event:{blur:emailCheck}" type="email" class="form-control" id="email" name="email" placeholder="Enter your email address">
          <small class="form-text text-muted">Your email is used to reset your password in case you forget it.</small>
          <div id="emailres" class="text-warning"></div>
        </div>
        <div class="form-group">
          <label for="dob">Date of birth</label>
          <input data-bind="value:dob" type="date" class="form-control" id="dob" name="dob" max="<%= java.time.LocalDate.now() %>">
        </div>
        <div class="mb-2">Gender</div>
        <div class="form-check">
          <input data-bind="checked:sex" type="radio" class="form-check-input" id="m" name="sex" value="male">
          <label class="form-check-label" for="m">Male</label>
        </div>
        <div class="form-check">
          <input data-bind="checked:sex" type="radio" class="form-check-input" id="f" name="sex" value="female">
          <label class="form-check-label" for="f">Female</label>
        </div>
      </form>
      <h5 class="mb-4">Privacy Policy and Terms</h5>
      <div class="border p-2 p-lg-4 mb-4">
        <strong class="d-block mb-2">By accepting these terms, you agree to be bound by them.</strong>
        <em class="d-block mb-2">The "service" refers to this application, recsforme.</em>
        <em class="d-block mb-2">"Information" refers to any data sent to the service, including but not limited to: this form, search queries, and the contents of your lists.</em>
        <ol class="list-group-ordered mb-2">
          <li>The service will not profit off of, or otherwise share your information with other parties.</li>
          <li>The service will take necessary measures to secure your information.</li>
          <li>You are to be held liable for your usage of the service.</li>
          <li>You will not take legal action against the owner of the service under any circumstance.</li>
        </ol>
      </div>
      <div class="form-check">
        <input data-bind="checked:accepted" type="checkbox" class="form-check-input" id="acc">
        <label class="form-check-label" for="acc">
          I accept the privacy policy and terms in their entirety.
        </label>
      </div>
      <span data-bind="visible:email()===''">
        <h6 class="text-danger mt-3">You will be unable to reset your password if you do not supply an email address.</h6>
      </span>
      <button data-bind="enable:valid(),click:sendInfo" type="submit" class="btn btn-primary btn-lg btn-block mt-3" disabled form="info-form" formmethod="POST">
        Sign Up
      </button>
      <div id="subres"></div>
    </main>
  </body>
</html>
