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
package me.recsfor.engine.recommend;

import java.util.LinkedHashMap;

/**
 * Abstract class containing the essential fields for recommendations.
 * @author lkitaev
 */
public abstract class AbstractRec {
  /**
   * The username and user ID.
   */
  protected String name, id;
  /**
   * True if the recommendations are for likes, false if they're for dislikes.
   */
  protected boolean status;
  /**
   * The generated recommendations.
   */
  protected LinkedHashMap<String, String> recs;
  
  public AbstractRec() {
    name = "";
    id = "";
    status = false;
    recs = null;
  }
  
  public AbstractRec(String name, String id, boolean like) {
    this.name = name;
    this.id = id;
    status = like;
    recs = new LinkedHashMap<>();
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
   * @return the status
   */
  public boolean getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public void setStatus(boolean status) {
    this.status = status;
  }
  /**
   * @return the recs
   */
  public LinkedHashMap<String, String> getRecs() {
    return recs;
  }
  /**
   * @param recs the recs to set
   */
  public void setRecs(LinkedHashMap<String, String> recs) {
    this.recs = recs;
  }
}
