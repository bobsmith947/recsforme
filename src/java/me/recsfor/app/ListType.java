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

/**
 * Class to define the array located with a user list.
 * @author lkitaev
 */
public class ListType {
  private ListModel[] list;
  
  public ListType() {
    list = new ListModel[0];
  }
  /**
   * @return the list
   */
  public ListModel[] getList() {
    return list;
  }
  /**
   * @param list the list to set
   */
  public void setList(ListModel[] list) {
    this.list = list;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (ListModel entry : list) {
      sb.append("name: ")
              .append(entry.getName())
              .append("\t id: ")
              .append(entry.getId())
              .append("\n");
    }
    return sb.toString();
  }
}
