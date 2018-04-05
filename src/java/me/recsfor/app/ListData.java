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

import java.util.LinkedHashMap;
import java.io.IOException;
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts JSON data of user likes/dislikes in Java objects.
 * @author lkitaev
 */
public class ListData {
  private ListType likeData;
  private ListType dislikeData;
  
  public ListData() {
    likeData = null;
    dislikeData = null;
  }
  
  public ListData(String likeList, String dislikeList) {
    //likeList = likeList.replace("'", "\\'");
    //dislikeList = dislikeList.replace("'", "\\'");
    map(likeList, dislikeList);
  }
  
  private void map(String likes, String dislikes) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      likeData = mapper.readValue(likes, ListType.class);
      dislikeData = mapper.readValue(dislikes, ListType.class);
      System.out.println(likeData);
      System.out.println(dislikeData);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  
  public static ListData createData(String likeList, String dislikeList) {
    return new ListData(likeList, dislikeList);
  }

  /**
   * @return the likeData
   */
  public ListType getLikeData() {
    return likeData;
  }

  /**
   * @param likeData the likeData to set
   */
  public void setLikeData(ListType likeData) {
    this.likeData = likeData;
  }

  /**
   * @return the dislikeData
   */
  public ListType getDislikeData() {
    return dislikeData;
  }

  /**
   * @param dislikeData the dislikeData to set
   */
  public void setDislikeData(ListType dislikeData) {
    this.dislikeData = dislikeData;
  }
}
