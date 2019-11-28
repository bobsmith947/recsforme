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
import java.util.LinkedHashSet;

/**
 * Represents a list of groups.
 * The list is actually implemented as a set.
 * Can convert data to and from JSON.
 * @author lkitaev
 */
public class ListData implements Serializable, Comparable {
  private static final long serialVersionUID = 177281769747262676L;
  private LinkedHashSet<ListGroup> list;
  
  public ListData() {
    list = new LinkedHashSet<>();
  }
  
  public ListData(LinkedHashSet<ListGroup> list) {
    this.list = list;
  }
  
  /**
   * @return the list
   */
  public LinkedHashSet<ListGroup> getList() {
    return list;
  }
    
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (obj.getClass() != this.getClass()) {
      return false;
    } else {
      return obj.hashCode() == this.hashCode();
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    ListGroup[] arr = new ListGroup[0];
    for (ListGroup g : this.list.toArray(arr))
      hash = 29 * hash + g.hashCode();
    return hash;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int count = 1;
    for (ListGroup g : list) {
      sb.append("Item ")
              .append(count)
              .append(": ")
              .append(g.toString())
              .append("\n");
      count++;
    }
    return sb.toString();
  }

  @Override
  // TODO rewrite this
  public int compareTo(Object t) {
    if (t == null) {
      throw new NullPointerException();
    } else if (!(t instanceof ListData)) {
      throw new ClassCastException();
    } else {
      ListData other = (ListData) t;
      int diff = 0;
      ListGroup[] groups = new ListGroup[0];
      for (ListGroup item : other.list.toArray(groups)) {
        if (!this.list.contains(item))
          diff--;
      }
      for (ListGroup item : this.list.toArray(groups)) {
        if (!other.list.contains(item))
          diff++;
      }
      return diff;
    }
  }
}
