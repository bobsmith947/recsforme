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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.servlet.jsp.jstl.sql.Result;
import me.recsfor.app.ListData;

/**
 * Used for generating recommendations.
 * @author lkitaev
 */
public class Generator {
  private LinkedList<User> users;
  
  /**
   * Creates an instance with an empty list of users for making recommendations.
   * Not very helpful.
   */
  public Generator() {
    users = new LinkedList<>();
  }
  /**
   * Creates an instance with a list of users from an already existing list.
   * @param users the users
   */
  public Generator(LinkedList<User> users) {
    this.users = users;
  }
  /**
   * Creates an instance with a list of users from a map of users and ID's from the database.
   * @param users the users
   */
  public Generator(LinkedHashMap<Integer, User> users) {
    this.users = new LinkedList<>(users.values());
  }
  
  /**
   * Adds users and their ID's from the database to a map.
   * This does not include the user lists.
   * @param results the database query results
   * @return the map of the users
   */
  public static LinkedHashMap<Integer, User> addUsers(Result results) {
    LinkedHashMap<Integer, User> users = new LinkedHashMap<>();
    Integer id;
    String name;
    String sex;
    LocalDate dob;
    for (int i = 0; i < results.getRowCount(); i++) {
      Object[][] resArr = results.getRowsByIndex();
      id = (Integer) resArr[i][0];
      name = (String) resArr[i][1];
      sex = (String) resArr[i][2];
      try {
        dob = LocalDate.parse(((Date) resArr[i][3]).toString());
      } catch (DateTimeParseException e) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        dob = LocalDate.of(1900, 1, 1);
      }
      users.put(id, new User(name, sex, dob));
    }
    return users;
  }
  /**
   * Adds like and dislike lists to a user.
   * @param user the user to add lists to
   * @param likeResult the likes list in the database
   * @param dislikeResult the dislikes list in the database
   * @return the user with the lists added
   */
  public static User addListsToUser(User user, Result likeResult, Result dislikeResult) {
    String likes = (String) likeResult.getRowsByIndex()[0][0];
    String dislikes = (String) dislikeResult.getRowsByIndex()[0][0];
    try {
      user.setLikes(ListData.mapData(likes));
      user.setDislikes(ListData.mapData(dislikes));
    } catch (IOException | NullPointerException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return user;
  }
  /**
   * Calculates the similarity between two pairs of lists.
   * Both parameters are expected to be length two arrays; one like list and one dislike list.
   * Neither list must be at a specific index, but the same indexes must correspond to the same list.
   * A result of 1.0 means the two contain all the same elements.
   * A result of 0.0 means the two do not share any elements.
   * @param one the first group of lists
   * @param two the second group of lists
   * @return a value between 0.0 and 1.0, inclusive, indicating the similarity
   */
  protected static double calculateSimilarity(ListData[] one, ListData[] two) {
    //TODO implement
    return 0.0;
  }
  /**
   * Ranks the list of users based on their similarity to the supplied likes and dislikes.
   * @param likes the likes of the user who is being ranked against
   * @param dislikes the dislikes of the user who is being ranked against
   */
  private void rankUsers(ListData likes, ListData dislikes) {
    ListData[] lists = {likes, dislikes};
    users.sort((User userOne, User userTwo) -> {
      ListData[] listsOne = {userOne.getLikes(), userOne.getDislikes()};
      ListData[] listsTwo = {userTwo.getLikes(), userTwo.getDislikes()};
      double diff = calculateSimilarity(listsOne, lists) - calculateSimilarity(listsTwo, lists);
      return (int) (diff * 100);
    });
  }

  /**
   * @return the users
   */
  public LinkedList<User> getUsers() {
    return users;
  }

  /**
   * @param users the users to set
   */
  public void setUsers(LinkedList<User> users) {
    this.users = users;
  }
}
