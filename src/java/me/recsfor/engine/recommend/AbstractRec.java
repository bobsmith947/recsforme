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
  protected String name, id;
  protected final boolean STATUS;
  protected LinkedHashMap<String, String> recs;
  
  public AbstractRec() {
    name = "";
    id = "";
    STATUS = false;
    recs = null;
  }
  
  public AbstractRec(String name, String id, boolean like) {
    this.name = name;
    this.id = id;
    STATUS = like;
    recs = new LinkedHashMap<>();
  }
  /**
   * Determines if the status is a like.
   * @return if it is a like
   */
  public final boolean isLike() {
    return STATUS == true;
  }
  /**
   * Determines if the status is a dislike.
   * @return if it is a dislike
   */
  public final boolean isDislike() {
    return STATUS == false;
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
   * @return the STATUS
   */
  public boolean getStatus() {
    return STATUS;
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
