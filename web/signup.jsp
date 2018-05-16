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
          <div id="checkres"></div>
        </div>
        <div class="form-group">
          <label for="pw">Password</label>
          <input data-bind="textInput:pw" type="password" class="form-control" id="pw" name="pw" placeholder="Enter a secure password" minlength="8" required>
          <small class="form-text text-muted">Don't use the same password you use elsewhere!</small>
          <input data-bind="enable:pw().length>=8,textInput:pwc" type="password" class="form-control mt-2" placeholder="Confirm your password" required>
        </div>
        <h5>Optional Fields</h5>
        <div class="form-group">
          <label for="email">Email address</label>
          <input data-bind="textInput:email" type="email" class="form-control" id="email" name="email" placeholder="Enter your email address">
          <small class="form-text text-muted">Your email is used to reset your password in case you forget it. We will never email you.</small>
          <small class="form-text text-muted">The email address you use should not be the same as your username for this site.</small>
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
      <div class="border p-2 p-lg-4 mb-4" style="height:50%;overflow-y:scroll">
        <strong class="d-block mb-2">By accepting these terms, you agree to be bound be them.</strong>
        <em class="d-block mb-2">The "service" refers to this application, recsforme.</em>
        <ol class="list-group-ordered mb-2">
          <li>The information you submit should be entirely accurate. Submitting false information only harms the service provided to you.</li>
          <li>The service will not profit off of your information in any way, shape, or form.</li>
          <li>The service will not share your information with any other group or person.</li>
          <li>You are to be held liable for your usage of the service.</li>
          <li>You will not take legal action against the owner of the service under any circumstance.</li>
          <li>Information stored by the service is not guaranteed to be completely safe, although necessary steps are taken to minimize the risk of data loss.</li>
          <li>In the unlikely event of data loss, the service will not assume responsibility for the information you have submitted.</li>
          <li>Only passwords are stored in an encrypted format. Any other information submitted should not be sensitive.</li>
          <li>Sensitive information includes your full name, home/work address, or other details you would not want given out to strangers.</li>
          <li>The owner of the service reserves all rights in accordance with the Apache License 2.0 (below).</li>
        </ol>
        <pre class="text-light">
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work.

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   Copyright 2018 Lucas Kitaev

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
        </pre>
      </div>
      <div data-bind="visible:!completed()" class="mt-2">
        <h5 class="text-warning">Please ensure all fields are valid before accepting.</h5>
      </div>
      <div class="form-check">
        <input data-bind="checked:accepted" type="checkbox" class="form-check-input" id="acc">
        <label class="form-check-label" for="acc">
          I accept the privacy policy and terms in their entirety.
        </label>
      </div>
      <div class="form-check">
        <input data-bind="checked:notRobot" type="checkbox" class="form-check-input" id="rob">
        <label class="form-check-label" for="rob">
          I am not a robot, and do not plan on becoming one.
        </label>
      </div>
      <div data-bind="visible:completed()" id="info-div" class="mt-3 text-info" style="display:none">
        <h6>Username: <span data-bind="text:uname"></span></h6>
        <span data-bind="visible:email()!==''">
          <h6>Email: <span data-bind="text:email"></span></h6>
        </span>
        <span data-bind="visible:email()===''">
          <h6 class="text-danger">You will be unable to reset your password if you do not supply an email address.</h6>
        </span>
        <span data-bind="visible:dob()!==''">
          <h6>Age: <span data-bind="text:age()"></span></h6>
        </span>
        <span data-bind="visible:sex()">
          <h6>Sex: <span data-bind="text:sex"></span></h6>
        </span>
        <div id="subres"></div>
      </div>
      <button data-bind="enable:completed(),click:sendInfo" type="submit" class="btn btn-primary btn-lg btn-block mt-3" disabled form="info-form" formmethod="POST">
        Sign Up
      </button>
    </main>
  </body>
</html>
