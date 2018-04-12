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
import java.util.Objects;
import me.recsfor.engine.search.*;
/**
 * Converts user like and dislike data from JavaScript Object Notation (JSON) to a Java object.
 * @author lkitaev
 */
public class ListData implements Serializable {
  private static final long serialVersionUID = 177281769747262676L;
  /**
   * Represents the JSON array of objects held under the "list" name.
   */
  private LinkedList<ListGroup> list;
  
  public ListData() {
    list = new LinkedList<>();
  }
  
  public ListData(LinkedList<ListGroup> list) {
    this.list = list;
  }
  /**
   * @return the list
   */
  public LinkedList<ListGroup> getList() {
    return list;
  }
  /**
   * @param list the list to set
   */
  public void setList(LinkedList<ListGroup> list) {
    this.list = list;
  }
  /**
   * Maps JSON data to an instance of a <code>ListData</code> object.
   * @param json the data
   * @return the mapped data
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
   * Converts an instance of a <code>ListData</code> object to JSON.
   * @param data the data
   * @return the data as a JSON string
   */
  public static String stringifyData(ListData data) {
    ObjectMapper mapper = new ObjectMapper();
    String ret;
    try {
      ret = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      ret = e.getMessage();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return ret;
  }
  /**
   * Creates a string representing the servlet context path of a group, based on its type.
   * @param groupType the type of the group
   * @return the group context
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
   * Creates a JSON string representing an item with the specified group properties.
   * @param name the group name
   * @param id the group id
   * @param type the group type
   * @return the group expressed in JSON
   */
  public static String generateItem(String name, String id, String type) {
    name = name.replace("'", "''");
    ListGroup item = new ListGroup(name, id, type);
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
  /**
   * Creates a <code>ListGroup</code> object representing an item with the specified JSON data.
   * @param json the data
   * @return the generated data
   * @throws IOException if a problem occurs when reading the input
   */
  public static ListGroup generateItem(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ListGroup group;
    try {
      group = mapper.readValue(json, ListGroup.class);
    } catch (JsonMappingException | JsonParseException e) {
      group = new ListGroup();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return group;
  }
  /**
   * Removes an item matching the specified group properties from the list.
   * @param name the group name
   * @param id the group id
   * @param type the group type
   */
  public void removeItem(String name, String id, String type) {
    ListGroup item = new ListGroup(name, id, type);
    list.remove(item);
  }
  /**
   * Removes an item matching the specified JSON data from the list.
   * @param json the data
   * @throws IOException if a problem occurs when reading the input
   */
  public void removeItem(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ListGroup group;
    try {
      group = mapper.readValue(json, ListGroup.class);
    } catch (JsonMappingException | JsonParseException e) {
      group = new ListGroup();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    list.remove(group);
  }
  /**
   * Checks if the list contains an item matching the specified group properties.
   * @param name the group name
   * @param id the group id
   * @param type the group type
   * @return whether the group exists or not
   */
  public boolean containsItem(String name, String id, String type) {
    ListGroup item = new ListGroup(name, id, type);
    return list.contains(item);
  }
  /**
   * Checks if the list contains an item matching the specified JSON.
   * @param json the data
   * @throws IOException if a problem occurs when reading the input
   * @return whether the group exists or not
   */
  public boolean containsItem(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ListGroup group;
    try {
      group = mapper.readValue(json, ListGroup.class);
    } catch (JsonMappingException | JsonParseException e) {
      group = new ListGroup();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return list.contains(group);
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
      //check if lists are the same
      return obj.hashCode() == this.hashCode();
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.list);
    return hash;
  }
}
