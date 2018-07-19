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
import java.util.LinkedHashSet;
import static java.util.Arrays.copyOf;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static me.recsfor.engine.search.Context.*;

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
  /**
   * @param list the list to set
   */
  public void setList(LinkedHashSet<ListGroup> list) {
    this.list = list;
  }
  
  /**
   * Maps JSON data to an instance of a <code>ListData</code> object.
   * @param json the data
   * @return the mapped data
   */  
  public static ListData mapData(String json) {
    ObjectMapper mapper = new ObjectMapper();
    ListData data;
    try {
      data = mapper.readValue(json, ListData.class);
    } catch (IOException e) {
      data = new ListData();
      System.err.println(e);
    }
    return data;
  }
  /**
   * Converts an instance of a <code>ListData</code> object to JSON.
   * @param data the data
   * @return the data as a JSON string
   */
  public static String stringifyData(ListData data) {
    if (data == null || data.list.isEmpty() || data.list == null)
      return "{\"list\":[]}";
    ObjectMapper mapper = new ObjectMapper();
    String ret;
    try {
      ret = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      ret = e.getMessage();
      System.err.println(e);
    }
    return ret;
  }
  /**
   * Merges the likes and dislikes from two pairs of lists into a single pair of lists.
   * Each pair is to be treated with likes first and dislikes second.
   * Conflicts are removed from the resulting lists.
   * @param listsOne the first pair
   * @param listsTwo the second pair
   * @return the lists merged into a length two array
   */
  public static ListData[] mergeLists(ListData[] listsOne, ListData[] listsTwo) {
    LinkedHashSet<ListGroup> likesList = new LinkedHashSet<>(listsOne[0].list);
    likesList.addAll(listsTwo[0].list);
    LinkedHashSet<ListGroup> dislikesList = new LinkedHashSet<>(listsOne[1].list);
    dislikesList.addAll(listsTwo[1].list);
    LinkedHashSet<ListGroup> conflicts = new LinkedHashSet<>(likesList);
    conflicts.retainAll(dislikesList);
    conflicts.forEach(item -> {
      likesList.remove(item);
      dislikesList.remove(item);
    });
    ListData[] ret = {new ListData(likesList), new ListData(dislikesList)};
    return ret;
  }
  
  /**
   * Creates a string representing the servlet context path of a group, based on its type.
   * @param groupType the type of the group
   * @return the group context
   */
  public static String generateContext(String groupType) {
    String movie = MOVIE.getContext();
    String artist = ARTIST.getContext();
    String album = ALBUM.getContext();
    //<editor-fold defaultstate="collapsed">
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
    //</editor-fold>
  }
  /**
   * Creates a JSON string representing an item with the specified group properties.
   * @param name the group name
   * @param id the group id
   * @param type the group type
   * @return the group expressed in JSON
   */
  public static String generateItem(String name, String id, String type) {
    ListGroup item = new ListGroup(name, id, type);
    String ret;
    ObjectMapper mapper = new ObjectMapper();
    try {
      ret = mapper.writeValueAsString(item);
    } catch (JsonProcessingException e) {
      ret = e.getMessage();
      System.err.println(e);
    }
    return ret;
  }
  /**
   * Creates a <code>ListGroup</code> object representing an item with the specified JSON data.
   * @param json the data
   * @return the generated data
   */
  public static ListGroup generateGroup(String json) {
    ObjectMapper mapper = new ObjectMapper();
    ListGroup group;
    try {
      group = mapper.readValue(json, ListGroup.class);
    } catch (IOException e) {
      group = new ListGroup();
      System.err.println(e);
    }
    return group;
  }
  /**
   * @param lists the lists
   * @return an array of the lists
   */
  public static ListData[] createArray(ListData... lists) {
    return copyOf(lists, lists.length);
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
