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

import java.io.Serializable;
import java.util.Objects;

/**
 * Defines the content of user like and dislike lists.
 * @author lkitaev
 */
public class ListGroup implements Serializable {
  private static final long serialVersionUID = -3414177256146753331L;
  private String name;
  private String id;
  private String type;
  
  public ListGroup() {
    name = "";
    id = "";
    type = "";
  }
  
  public ListGroup(String name, String id, String type) {
    this.name = name;
    this.id = id;
    this.type = type;
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
    this.name = name;
  }
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }  
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * @return all properties of a group
   */
  public String[] getAllProps() {
    String[] ret = new String[3];
    ret[0] = this.name;
    ret[1] = this.id;
    ret[2] = this.type;
    return ret;
  }
  /**
   * Sets all properties of a group.
   * @param name the name
   * @param id the id
   * @param type the type
   */
  public void setAllProps(String name, String id, String type) {
    this.name = name;
    this.id = id;
    this.type = type;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      //ensure it's not null
      return false;
    } else if (obj == this) {
      //check if reference is the same
      return true;
    } else if (obj.getClass() != this.getClass()) {
      //compare classes rather than instanceof
      //if this class is extended, children would be equal
      return false;
    } else {
      //check if fields share the same values
      return obj.hashCode() == this.hashCode();
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.name);
    hash = 97 * hash + Objects.hashCode(this.id);
    hash = 97 * hash + Objects.hashCode(this.type);
    return hash;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("name: ")
            .append(name)
            .append("\n id: ")
            .append(id)
            .append("\n type: ")
            .append(type)
            .append("\n");
    return sb.toString();
  }
}
