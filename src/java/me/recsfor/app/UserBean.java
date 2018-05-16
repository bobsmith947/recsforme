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
package me.recsfor.app;

import java.beans.*;
import java.io.Serializable;
/**
 * JavaBeans component to store user data.
 * @author lkitaev
 */
public class UserBean implements Serializable {
  private static final long serialVersionUID = 2857157327400526226L;
  public static final String PROP_NAME = "name";
  public static final String PROP_ID = "id";
  public static final String PROP_LOGGEDIN = "loggedIn";
  public static final String PROP_TRIES = "tries";
  public static final String PROP_LIKEDATA = "likeData";
  public static final String PROP_DISLIKEDATA = "dislikeData";
  private String name;
  private int id;
  private boolean loggedIn;
  private short tries;
  private ListData likeData;
  private ListData dislikeData;
  private final PropertyChangeSupport propertySupport;
  
  public UserBean() {
    name = "";
    id = -1;
    loggedIn = false;
    tries = 0;
    likeData = new ListData();
    dislikeData = new ListData();
    propertySupport = new PropertyChangeSupport(this);
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
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
    String oldName = this.name;
    this.name = name;
    propertySupport.firePropertyChange(PROP_NAME, oldName, name);
  }
  /**
   * @return the id
   */
  public int getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(int id) {
    int oldId = this.id;
    this.id = id;
    propertySupport.firePropertyChange(PROP_ID, oldId, id);
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
   * @return the tries
   */
  public short getTries() {
    return tries;
  }
  /**
   * @param tries the tries to set
   */
  public void setTries(short tries) {
    short oldTries = this.tries;
    this.tries = tries;
    propertySupport.firePropertyChange(PROP_TRIES, oldTries, tries);
  }
  /**
   * @return the likeData
   */
  public ListData getLikeData() {
    return likeData;
  }
  /**
   * @param likeData the likeData to set
   */
  public void setLikeData(ListData likeData) {
    ListData oldLikeData = this.likeData;
    this.likeData = likeData;
    propertySupport.firePropertyChange(PROP_LIKEDATA, oldLikeData, likeData);
  }
  /**
   * @return the dislikeData
   */
  public ListData getDislikeData() {
    return dislikeData;
  }
  /**
   * @param dislikeData the dislikeData to set
   */
  public void setDislikeData(ListData dislikeData) {
    ListData oldDislikeData = this.dislikeData;
    this.dislikeData = dislikeData;
    propertySupport.firePropertyChange(PROP_DISLIKEDATA, oldDislikeData, dislikeData);
  }
}
