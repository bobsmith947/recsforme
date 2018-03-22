<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Sign Up</title>
  <body>
    <noscript class="alert alert-danger">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <form data-bind="submit:sendInfo" method="POST" onsubmit="this.preventDefault()">
        <div class="form-group">
          <label for="uname">Username</label>
          <input data-bind="value:uname" type="text" class="form-control" id="uname" name="uname" placeholder="Enter your desired username" maxlength="36" required>
          <small class="form-text text-muted">Maximum 36 alphanumeric characters.</small>
          <a href="#">Click here to check for availability.</a>
        </div>
        <div class="form-group">
          <label for="pw">Password</label>
          <input data-bind="value:pw" type="password" class="form-control" id="pw" name="pw" placeholder="Enter your desired password" maxlength="36" required>
          <small class="form-text text-muted">Don't use the same password you use elsewhere!</small>
          <a href="#">Click here for detailed requirements.</a>
          <div class="mb-2"></div>
          <input data-bind="enable:pw().length>0,value:pwc" type="password" class="form-control" name="pwc" placeholder="Confirm your password" maxlength="36" required>
        </div>
        <h4>All fields below are optional</h4>
        <div class="form-group">
          <label for="email">Email address</label>
          <input data-bind="value:email" type="email" class="form-control" id="email" name="email" placeholder="Enter your email">
          <small class="form-text text-muted">You can use your email to reset your password if you ever forget it.</small>
        </div>
        <div class="form-group">
          <label for="dob">Date of birth</label>
          <input data-bind="value:dob" type="date" class="form-control" id="dob" name="dob">
          <small class="form-text text-muted">Your date of birth can be used to tailor recommendations to match your age group.</small>
        </div>
        <%-- use sex and gender interchangeably to piss certain people off --%>
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
        <button data-bind="enable:completed" type="submit" class="btn btn-primary btn-lg btn-block mt-3">Sign Up</button>
      </form>
      <div data-bind="visible:completed" id="info">
        <h6>Username: <span data-bind="text:uname"></span></h6>
        <h6>Email: <span data-bind="text:email"></span></h6>
        <h6>Age: <span data-bind="text:age"></span></h6>
        <h6>Sex: <span data-bind="text:sex"></span></h6>
      </div>
    </main>
  </body>
</html>
