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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import javax.servlet.jsp.jstl.sql.Result;
import me.recsfor.app.ListData;
import me.recsfor.app.ListGroup;

/**
 * Used for generating recommendations.
 * @author lkitaev
 */
public class Generator {
  private final ArrayList<User> users;
  private ListData knownLikes, knownDislikes;
  
  /**
   * Creates an instance with an empty list of users and no known likes or dislikes.
   * Not very helpful for making recommendations.
   */
  public Generator() {
    users = new ArrayList<>();
    knownLikes = null;
    knownDislikes = null;
  }
  /**
   * Creates an instance with a list of users from an already existing list and no known likes or dislikes.
   * @param users the users
   */
  public Generator(ArrayList<User> users) {
    this.users = users;
    knownLikes = null;
    knownDislikes = null;
  }
  /**
   * Creates an instance with a list of users from an already existing list and likes and dislikes.
   * @param users the users
   * @param likes the known likes
   * @param dislikes the known dislikes
   */
  public Generator(ArrayList<User> users, ListData likes, ListData dislikes) {
    this(users);
    knownLikes = likes;
    knownDislikes = dislikes;
  }
  /**
   * Creates an instance with a list of users from a map of users and ID's from the database and no known likes or dislikes.
   * @param users the users
   */
  public Generator(HashMap<Integer, User> users) {
    this.users = new ArrayList<>(users.values());
    knownLikes = null;
    knownDislikes = null;
  }
  /**
   * Creates an instance with a list of users from a map of users and ID's from the database and already existing likes and dislikes.
   * @param users the users
   * @param likes the known likes
   * @param dislikes the known dislikes
   */
  public Generator(HashMap<Integer, User> users, ListData likes, ListData dislikes) {
    this(users);
    knownLikes = likes;
    knownDislikes = dislikes;
  }
  
  /**
   * @return the users
   */
  public ArrayList<User> getUsers() {
    return users;
  }
  /**
   * @return the knownLikes
   */
  public ListData getKnownLikes() {
    return knownLikes;
  }
  /**
   * @return the knownDislikes
   */
  public ListData getKnownDislikes() {
    return knownDislikes;
  }
  
  /**
   * Adds users and their ID's from the database to a map.
   * This does not include the user lists.
   * @param results the database query results
   * @return the map of the users
   */
  public static HashMap<Integer, User> addUsers(Result results) {
    HashMap<Integer, User> users = new HashMap<>();
    Object[][] resArr;
    Integer id;
    String name;
    String sex;
    LocalDate dob;
    for (int i = 0; i < results.getRowCount(); i++) {
      resArr = results.getRowsByIndex();
      id = (Integer) resArr[i][0];
      name = (String) resArr[i][1];
      sex = (String) resArr[i][2];
      try {
        dob = LocalDate.parse(resArr[i][3].toString());
      } catch (DateTimeParseException e) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        //use the default date
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
    user.setLikes(ListData.mapData(likes));
    user.setDislikes(ListData.mapData(dislikes));
    return user;
  }
  /**
   * Calculates the similarity between two pairs of lists.
   * Both parameters are expected to be length two arrays.
   * The first index should be the like list and the second index should be the dislike list.
   * A result of 1.0 means the two pairs of lists are exactly the same.
   * A result of -1.0 means the two pairs of lists are completely opposite.
   * @param one the first pair of lists
   * @param two the second pair of lists
   * @return a decimal between -1 and 1, inclusive, that indicates the similarity
   */
  public static double calculateSimilarity(ListData[] one, ListData[] two) {
    //intersect the likes
    HashSet<ListGroup> likeIntersection = new HashSet<>(one[0].getList());
    likeIntersection.retainAll(two[0].getList());
    //intersect the dislikes
    HashSet<ListGroup> dislikeIntersection = new HashSet<>(one[1].getList());
    dislikeIntersection.retainAll(two[1].getList());
    //intersect likes in the first with dislikes in the second
    HashSet<ListGroup> conflictOne = new HashSet<>(one[0].getList());
    conflictOne.retainAll(two[1].getList());
    //intersect likes in the second with dislikes in the first
    HashSet<ListGroup> conflictTwo = new HashSet<>(one[1].getList());
    conflictTwo.retainAll(two[0].getList());
    //union the likes and dislikes
    HashSet<ListGroup> union = new HashSet<>(one[0].getList());
    union.addAll(one[1].getList());
    union.addAll(two[0].getList());
    union.addAll(two[1].getList());
    //get the sizes of each intersected/unioned set
    double i1 = likeIntersection.size();
    double i2 = dislikeIntersection.size();
    double c1 = conflictOne.size();
    double c2 = conflictTwo.size();
    double u = union.size();
    //Jaccard index formula
    return (i1 + i2 - c1 - c2) / u;
  }
  /**
   * Ranks the list of users based on their similarity to the known likes and dislikes.
   * The list will be sorted in descending order.
   */
  private void rankUsers() {
    final ListData[] lists = {knownLikes, knownDislikes};
    users.sort((User userOne, User userTwo) -> {
      ListData[] listsOne = {userOne.getLikes(), userOne.getDislikes()};
      ListData[] listsTwo = {userTwo.getLikes(), userTwo.getDislikes()};
      double simOne = calculateSimilarity(listsOne, lists);
      double simTwo = calculateSimilarity(listsTwo, lists);
      if (simOne > simTwo)
        return 1;
      else if (simOne < simTwo)
        return -1;
      else
        return 0;
    });
    Collections.reverse(users);
  }
  /**
   * Generates recommendations using a sorted list of users.
   * @param limit the number of recommendations to generate (defaults to 10 if this is 0)
   * @return a list of recommendations
   */
  public ListData listRecommendations(int limit) {
    int num;
    if (limit == 0)
      num = 10;
    else
      num = Math.abs(limit);
    if (knownLikes == null)
      knownLikes = new ListData();
    if (knownDislikes == null)
      knownDislikes = new ListData();
    rankUsers();
    ArrayDeque<User> userQueue = new ArrayDeque<>(users);
    User user;
    ListData recList = new ListData();
    while (recList.getList().size() < num && !userQueue.isEmpty()) {
      user = userQueue.pop();
      user.getLikes().getList().forEach(group -> {
        if (!(knownLikes.getList().contains(group) || knownDislikes.getList().contains(group)))
          recList.getList().add(group);
      });
    }
    return recList;
  }
  /**
   * Generates a random list of items.
   * @param limit the number of groups to add (defaults to 5 if this is 0)
   * @return a random list
   */
  public ListData listRandom(int limit) {
    int num;
    if (limit == 0)
      num = 5;
    else
      num = Math.abs(limit);
    Collections.shuffle(users);
    ArrayDeque<User> userQueue = new ArrayDeque<>(users);
    User user;
    ListData randList = new ListData();
    int count = 0;
    int rand;
    ArrayList<ListGroup> tempList;
    while (randList.getList().size() < num && !userQueue.isEmpty()) {
      //if (count >= users.size())
        //count = 0;
      //user = users.get(count);
      user = userQueue.pop();
      if (count % 2 == 0) {
        tempList = new ArrayList<>(user.getLikes().getList());
        if (tempList.size() > 0) {
          rand = (int) (Math.random() * tempList.size());
          randList.getList().add(tempList.get(rand));
        }
      } else {
        tempList = new ArrayList<>(user.getDislikes().getList());
        if (tempList.size() > 0) {
          rand = (int) (Math.random() * tempList.size());
          randList.getList().add(tempList.get(rand));
        }
      }
      count++;
    }
    return randList;
  }
}
