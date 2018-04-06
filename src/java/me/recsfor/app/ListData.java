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
import java.io.IOException;
//import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts user like and dislike data from JavaScript Object Notation (JSON) to a Java object.
 * @author lkitaev
 */
public class ListData implements Serializable {
  private static final long serialVersionUID = 177281769747262676L;
  private ListModel[] list;
  
  public ListData() {
    list = new ListModel[0];
  }
  /**
   * Maps parsed JSON data to an instance of a <code>ListData</code> object.
   * @param json the data to parse
   * @return a Java representation of the data
   * @throws IOException if a problem occurs when reading the input
   */  
  public static ListData mapData(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ListData data;
    try {
      data = mapper.readValue(json, ListData.class);
      System.out.println(data.toString());
    } catch (JsonMappingException | JsonParseException e) {
      data = new ListData();
      System.out.println(e.getMessage());
    }
    return data;
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
