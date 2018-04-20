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
package me.recsfor.engine.search;

import java.beans.*;
import java.io.Serializable;
import static org.apache.commons.lang3.text.WordUtils.capitalize;
/**
 * JavaBeans component to delegate queries to the proper class.
 * @author lkitaev
 */
public class QueryBean implements Serializable {
  private static final long serialVersionUID = -2224562734989733429L;
  public static final String PROP_QUERY = "query";
  public static final String PROP_TYPE = "type";
  public static final String PROP_CONTEXT = "context";
  public static final String PROP_DELEGATION = "delegation";
  private String query;
  private String type;
  private String context;
  private transient BasicQuery delegation;
  private final PropertyChangeSupport propertySupport;
  
  public QueryBean() {
    propertySupport = new PropertyChangeSupport(this);
    query = "";
    //default to a movie type query
    type = "movie";
    context = MovieQuery.CONTEXT;
    delegation = new MovieQuery();
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertySupport.removePropertyChangeListener(listener);
  }
  
  /**
   * @return the query
   */
  public String getQuery() {
    return query;
  }
  /**
   * @param query the query to set
   */
  public void setQuery(String query) {
    String oldQuery = this.query;
    this.query = query.trim();
    propertySupport.firePropertyChange(PROP_QUERY, oldQuery, query);
  }
  /**
   * @return the type
   */
  public String getType() {
    //format as title case so it displays nicely
    return capitalize(type);
  }
  /**
   * @param type the type to set
   */
  public void setType(String type) {
    String oldType = this.type;
    this.type = type;
    propertySupport.firePropertyChange(PROP_TYPE, oldType, type);
  }
  /**
   * @return the context
   */
  public String getContext() {
    return context;
  }
  /**
   * @param context the context to set
   */
  public void setContext(String context) {
    String oldContext = this.context;
    this.context = context;
    propertySupport.firePropertyChange(PROP_CONTEXT, oldContext, context);
  }
  /**
   * @return the delegation
   */
  public BasicQuery getDelegation() {
    return delegation;
  }
  /**
   * @param delegation the delegation to set
   */
  public void setDelegation(BasicQuery delegation) {
    BasicQuery oldDelegation = this.delegation;
    this.delegation = delegation;
    propertySupport.firePropertyChange(PROP_DELEGATION, oldDelegation, delegation);
  }
  
  /**
   * Creates a MovieQuery that can be referenced from a JSP.
   * @deprecated Use delegateQuery() instead
   * @return a new MovieQuery with the instance query
   */
  @Deprecated
  public MovieQuery sendMovieQuery() {
    return new MovieQuery(query);
  }
  /**
   * Creates an ArtistQuery that can be referenced from a JSP.
   * @deprecated Use delegateQuery() instead
   * @return a new AlbumQuery with the instance query
   */
  @Deprecated
  public ArtistQuery sendArtistQuery() {
    return new ArtistQuery(query);
  }
  /**
   * Creates an AlbumQuery that can be referenced from a JSP.
   * @deprecated Use delegateQuery() instead
   * @return a new AlbumQuery with the instance query
   */
  @Deprecated
  public AlbumQuery sendAlbumQuery() {
    return new AlbumQuery(query);
  }
  /**
   * Creates an instance the proper query based on the <code>type</code>.
   * Can be used to reference queries from a JSP.
   * @return a new child instance of an BasicQuery with the instance query
   */
  public BasicQuery delegateQuery() {
    switch (type) {
      case "movie":
        context = MovieQuery.CONTEXT;
        return new MovieQuery(query);
      case "artist":
        context = ArtistQuery.CONTEXT;
        return new ArtistQuery(query);
      case "album":
        context = AlbumQuery.CONTEXT;
        return new AlbumQuery(query);
      default:
        return null;
    }
  }
  
  /**
   * Checks if one query string is different from another.
   * @param oldQuery the old query
   * @param newQuery the new query
   * @return true if the two are different, false otherwise
   */
  public static boolean changed(String oldQuery, String newQuery) {
    //matches punctuation and articles
    String rep = "(([:\\-.,/])|(\\bthe\\b)|(\\ba\\b|\\ban\\b))+";
    //replaces each match in each query with nothing
    oldQuery = oldQuery.toLowerCase().replaceAll(rep, "").trim();
    newQuery = newQuery.toLowerCase().replaceAll(rep, "").trim();
    //check if the modified queries are still the same
    return !oldQuery.equals(newQuery);
  }
}
