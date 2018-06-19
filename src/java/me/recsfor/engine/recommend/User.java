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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import me.recsfor.app.ListData;

/**
 * Represents any user and his or her likes and dislikes.
 * Can be used to find the similarity with another user, in order to make recommendations.
 * @author lkitaev
 */
public class User {
  private String name;
  private Sex sex;
  private short age;
  private ListData likes;
  private ListData dislikes;
  
  public User() {
    name = "unknown";
    sex = Sex.UNKNOWN;
    age = -1;
    likes = null;
    dislikes = null;
  }
  
  public User(String uname, String sex, LocalDate dob, String likeList, String dislikeList) {
    name = uname;
    this.sex = sex != null && !sex.isEmpty() ? Sex.valueOf(sex.toUpperCase()) : Sex.UNKNOWN;
    age = (dob.isEqual(LocalDate.of(1900, 1, 1))) ? -1 : 
            (short) dob.until(LocalDate.now()).getYears(); //the default date
    try {
      likes = ListData.mapData(likeList);
      dislikes = ListData.mapData(dislikeList);
    } catch (IOException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      likes = new ListData();
      dislikes = new ListData();
    }
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
   * @return the sex
   */
  public Sex getSex() {
    return sex;
  }
  /**
   * @param sex the sex to set
   */
  public void setSex(Sex sex) {
    this.sex = sex;
  }
  /**
   * @return the age
   */
  public short getAge() {
    return age;
  }
  /**
   * @param age the age to set
   */
  public void setAge(short age) {
    this.age = age;
  }
  /**
   * @return the likes
   */
  public ListData getLikes() {
    return likes;
  }
  /**
   * @param likes the likes to set
   */
  public void setLikes(ListData likes) {
    this.likes = likes;
  }
  /**
   * @return the dislikes
   */
  public ListData getDislikes() {
    return dislikes;
  }
  /**
   * @param dislikes the dislikes to set
   */
  public void setDislikes(ListData dislikes) {
    this.dislikes = dislikes;
  }
  
  public float calculateSimilarity(User otherUser) {
    //TODO implement this
    return 0.0f;
  }
}
