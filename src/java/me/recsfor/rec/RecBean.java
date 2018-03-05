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
package me.recsfor.rec;

import java.beans.*;
import java.io.Serializable;

/**
 * JavaBeans component to assist with recommendation generation.
 * @author lkitaev
 */
public class RecBean implements Serializable {
  private static final long serialVersionUID = 8208767349207251619L;
  public static final String PROP_NAME = "name";
  public static final String PROP_ID = "id";
  public static final String PROP_LIKE = "like";
  private String name;
  private String id;
  private boolean like;
  private final PropertyChangeSupport propertySupport;
  
  public RecBean() {
    propertySupport = new PropertyChangeSupport(this);
    name = "";
    id = "";
    like = false;
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  /**
   * @param value the value to set
   */
  public void setId(String value) {
    String oldValue = id;
    id = value;
    propertySupport.firePropertyChange(PROP_ID, oldValue, id);
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
    java.lang.String oldName = this.name;
    this.name = name;
    propertySupport.firePropertyChange(PROP_NAME, oldName, name);
  }
  /**
   * @return the like
   */
  public boolean isLike() {
    return like;
  }
  /**
   * @param like the like to set
   */
  public void setLike(boolean like) {
    boolean oldLike = this.like;
    this.like = like;
    propertySupport.firePropertyChange(PROP_LIKE, oldLike, like);
  }
}
