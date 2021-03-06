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

import java.beans.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.LinkedHashSet;
import javax.servlet.jsp.jstl.sql.Result;
import me.recsfor.app.ListData;
import me.recsfor.app.ListGroup;

/**
 * JavaBeans component to store recommendation data.
 * @author lkitaev
 */
public class RecommendationBean implements Serializable {

	private static final long serialVersionUID = -7420405811924841508L;
	public static final String PROP_USERS = "users";
	public static final String PROP_RECOMMENDATIONS = "recommendations";
	private HashMap<Integer, User> users;
	private ListData recommendations;
	private final PropertyChangeSupport propertySupport;

	public RecommendationBean() {
		users = null;
		recommendations = null;
		propertySupport = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	public void addUsers(Result results) {
		users = new HashMap<>();
		User user;
		ListGroup group;
		Integer id;
		for (SortedMap row : results.getRows()) {
			id = (Integer) row.get("id");
			group = new ListGroup(row.get("name").toString(),
					row.get("gid").toString(), row.get("type").toString());
			user = users.getOrDefault(id, new User(row.get("name").toString()));
			if ((Boolean) row.get("liked")) {
				user.getLikes().getList().add(group);
			} else {
				user.getDislikes().getList().add(group);
			}
			users.put(id, user);
		}
	}

	public void addRecs(Result results) {
		LinkedHashSet<ListGroup> recs = new LinkedHashSet<>();
		for (SortedMap row : results.getRows()) {
			recs.add(new ListGroup(row.get("name").toString(),
				row.get("gid").toString(), row.get("type").toString()));
		}
		setRecommendations(new ListData(recs));
	}

	/**
	 * @return the users
	 */
	public HashMap<Integer, User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(HashMap<Integer, User> users) {
		HashMap<Integer, User> oldUsers = this.users;
		this.users = users;
		propertySupport.firePropertyChange(PROP_USERS, oldUsers, users);
	}

	/**
	 * @return the recommendations
	 */
	public ListData getRecommendations() {
		return recommendations;
	}

	/**
	 * @param recommendations the recommendations to set
	 */
	public void setRecommendations(ListData recommendations) {
		ListData oldRecommendations = this.recommendations;
		this.recommendations = recommendations;
		propertySupport.firePropertyChange(PROP_RECOMMENDATIONS, oldRecommendations, recommendations);
	}
}
