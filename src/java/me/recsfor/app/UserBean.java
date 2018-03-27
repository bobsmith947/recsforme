/*
 * Copyright 2018 lkitaev.
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
package me.recsfor.app;

import java.beans.*;
import java.io.Serializable;
/**
 * JavaBeans component to track user sessions.
 * @author lkitaev
 */
public class UserBean implements Serializable {
  private static final long serialVersionUID = 2857157327400526226L;
  public static final String PROP_NAME = "name";
  public static final String PROP_PASS = "pass";
  public static final String PROP_LOGGEDIN = "loggedIn";
  public static final String PROP_MESSAGE = "message";
  public static final String PROP_TRIES = "tries";
  private String name;
  private String pass;
  private boolean loggedIn;
  private byte tries;
  private String message;
  private final PropertyChangeSupport propertySupport;
  
  public UserBean() {
    this.name = "";
    this.pass = "";
    this.loggedIn = false;
    this.message = "";
    this.tries = 0;
    propertySupport = new PropertyChangeSupport(this);
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    String oldValue = this.name;
    this.name = name;
    propertySupport.firePropertyChange(PROP_NAME, oldValue, name);
  }
  /**
   * @return the pass
   */
  public String getPass() {
    return pass;
  }
  /**
   * @param pass the pass to set
   */
  public void setPass(String pass) {
    java.lang.String oldPass = this.pass;
    this.pass = pass;
    propertySupport.firePropertyChange(PROP_PASS, oldPass, pass);
  }
  /**
   * @return the loggedIn status
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }
  /**
   * @param loggedIn the loggedIn status to set
   */
  public void setLoggedIn(boolean loggedIn) {
    boolean oldLoggedIn = this.loggedIn;
    this.loggedIn = loggedIn;
    propertySupport.firePropertyChange(PROP_LOGGEDIN, oldLoggedIn, loggedIn);
  }
  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }
  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    java.lang.String oldMessage = this.message;
    this.message = message;
    propertySupport.firePropertyChange(PROP_MESSAGE, oldMessage, message);
  }
  /**
   * @return the tries
   */
  public byte getTries() {
    return tries;
  }
  /**
   * @param tries the tries to set
   */
  public void setTries(byte tries) {
    byte oldTries = this.tries;
    this.tries = tries;
    propertySupport.firePropertyChange(PROP_TRIES, oldTries, tries);
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }
}
