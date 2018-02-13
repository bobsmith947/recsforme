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
package me.recsfor.search;

import java.beans.*;
import java.io.Serializable;

/**
 * JavaBeans component to delegate queries to the proper class.
 * @author lkitaev
 */
public class QueryBean implements Serializable {
  public static final String PROP_TYPE = "type";
  public static final String PROP_QUERY = "query";
  private static final long serialVersionUID = -2224562734989733429L; //just in case
  private String type;
  private String query;
  private PropertyChangeSupport propertySupport;
  
  
  public QueryBean() {
    propertySupport = new PropertyChangeSupport(this);
    type = "movie";
    query = "";
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String value) {
    String oldValue = type;
    type = value;
    propertySupport.firePropertyChange(PROP_TYPE, oldValue, type);
  }
  
  public String getQuery() {
    return query;
  }
  
  public void setQuery(String value) {
    String oldValue = query;
    query = value;
    propertySupport.firePropertyChange(PROP_QUERY, oldValue, query);
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }
  
  public MovieQuery sendMovieQuery() {
    return new MovieQuery(query);
  }
  
  public ArtistQuery sendArtistQuery() {
      return new ArtistQuery(query);
  }
  
  public AlbumQuery sendAlbumQuery() {
      return new AlbumQuery(query);
  }
  
  public SongQuery sendSongQuery() {
      return new SongQuery(query);
  }
}
