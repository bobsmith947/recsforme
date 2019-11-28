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
import static java.util.Objects.hash;
import me.recsfor.engine.search.Context;

/**
 * Represents a group in a list.
 * Each group has a name, ID (from either IMDb or MusicBrainz), and type.
 * @author lkitaev
 */
public class ListGroup implements Serializable {
  private static final long serialVersionUID = -3414177256146753331L;
  private String name;
  private String id;
  private Context type;
  
  public ListGroup() {
    name = "";
    id = "";
    type = Context.BASIC;
  }
  
  public ListGroup(String name, String id, String type) {
    this.name = name;
    this.id = id;
    this.type = Context.valueOf(type.toUpperCase());
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
  public Context getType() {
    return type;
  }
  /**
   * @param type the type to set
   */
  public void setType(Context type) {
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
    //TODO consider using just the ID
    hash = 97 * hash + hash(this.id);
    return hash;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("name: ")
            .append(name)
            .append("\t id: ")
            .append(id)
            .append("\t type: ")
            .append(type);
    return sb.toString();
  }
}
