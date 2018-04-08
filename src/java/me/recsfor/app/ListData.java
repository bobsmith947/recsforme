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
import java.util.Arrays;
import java.util.LinkedList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recsfor.engine.search.*;
/**
 * Converts user like and dislike data from JavaScript Object Notation (JSON) to a Java object.
 * @author lkitaev
 */
public class ListData implements Serializable {
  private static final long serialVersionUID = 177281769747262676L;
  private LinkedList<ListModel> list;
  
  public ListData() {
    list = null;
  }
  
  public ListData(LinkedList<ListModel> list) {
    this.list = list;
  }
  /**
   * @return the list
   */
  public LinkedList<ListModel> getList() {
    return list;
  }
  /**
   * @param list the list to set
   */
  public void setList(LinkedList<ListModel> list) {
    this.list = list;
  }
  /**
   * Maps JSON data to an instance of a <code>ListData</code> object.
   * @param json the data to parse
   * @return the data as a Java object
   * @throws IOException if a problem occurs when reading the input
   */  
  public static ListData mapData(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ListData data;
    try {
      data = mapper.readValue(json, ListData.class);
    } catch (JsonMappingException | JsonParseException e) {
      data = new ListData();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return data;
  }
  /**
   * Creates a string representing the context path of a group, based on its type.
   * @param groupType the type of the group
   * @return the context
   */
  public static String generateContext(String groupType) {
    String movie = MovieQuery.CONTEXT;
    String artist = ArtistQuery.CONTEXT;
    String album = AlbumQuery.CONTEXT;
    switch (groupType.toLowerCase()) {
      case "movie":
        return movie;
      case "series":
        return movie;
      case "game":
        return movie;
      case "person":
        return artist;
      case "group":
        return artist;
      case "orchestra":
        return artist;
      case "choir":
        return artist;
      case "character":
        return artist;
      case "album":
        return album;
      case "single":
        return album;
      case "ep":
        return album;
      case "broadcast":
        return album;
      case "compilation":
        return album;
      case "soundtrack":
        return album;
      case "spokenword":
        return album;
      case "interview":
        return album;
      case "audiobook":
        return album;
      case "live":
        return album;
      case "remix":
        return album;
      case "dj-mix":
        return album;
      case "mixtape/street":
        return album;
      default:
        return "search.jsp?query=";
    }
  }
  /**
   * Creates a JSON string representing a group.
   * @param name the group name
   * @param id the group id
   * @param type the group type
   * @return the item
   */
  public static String generateItem(String name, String id, String type) {
    name = name.replace("'", "''");
    ListModel item = new ListModel(name, id, type);
    String ret;
    ObjectMapper mapper = new ObjectMapper();
    try {
      ret = mapper.writeValueAsString(item);
    } catch (JsonProcessingException e) {
      ret = e.getMessage();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return ret;
  }
}
