<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Sign Up</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <h2>Account Registration</h2>
      <form id="info-form">
        <div class="form-group">
          <label for="uname">Username</label>
          <input data-bind="textInput:uname,event:{blur:nameCheck}" type="text" class="form-control" id="uname" name="uname" placeholder="Enter your desired username" maxlength="18" pattern="\w+" autofocus required>
          <small class="form-text text-muted">Only alphanumeric characters are allowed.</small>
          <div id="nameres"></div>
        </div>
        <div class="form-group">
          <label for="pw">Password</label>
          <input data-bind="textInput:pw" type="password" class="form-control" id="pw" name="pw" placeholder="Enter a secure password" minlength="8" required>
          <small class="form-text text-muted">Your password must be at least 8 characters.</small>
          <input data-bind="enable:pw().length>=8,textInput:pwc,event:{blur:passCheck}" type="password" class="form-control mt-2" placeholder="Confirm your password" required>
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
          <small class="form-text text-muted">Your date of birth can be used to tailor recommendations to match your age group.</small>
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
        <small class="form-text text-muted">Your gender can be used to tailor recommendations to match others of your sex.</small>
      </form>
      <h5 class="mb-4">Privacy Policy and Terms</h5>
      <div class="border p-2 p-lg-4 mb-4">
        <strong class="d-block mb-2">By accepting these terms, you agree to be bound be them.</strong>
        <em class="d-block mb-2">The "service" refers to this application, recsforme.</em>
        <ol class="list-group-ordered mb-2">
          <li>The information you submit should be entirely accurate. Submitting false information only harms the service provided to you.</li>
          <li>The service will not profit off of your information in any way, shape, or form.</li>
          <li>The service will not share your information with any other group or person.</li>
          <li>You are to be held liable for your usage of the service.</li>
          <li>You will not take legal action against the owner of the service under any circumstance.</li>
          <li>Only passwords are stored in an encrypted format. Any other information submitted should not be sensitive.</li>
          <li>Sensitive information includes your full name, home/work address, or other details you would not want given out to strangers.</li>
          <li>The owner of the service reserves all rights in accordance with the Apache License 2.0 (below).</li>
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
