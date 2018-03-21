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
  private String name;
  private String pass;
  private final PropertyChangeSupport propertySupport;
  
  public UserBean() {
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
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }
}
