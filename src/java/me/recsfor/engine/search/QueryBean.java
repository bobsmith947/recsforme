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
 * JavaBeans component to store query data.
 * @author lkitaev
 */
public class QueryBean implements Serializable {
  private static final long serialVersionUID = -2224562734989733429L;
  public static final String PROP_QUERY = "query";
  public static final String PROP_TYPE = "type";
  public static final String PROP_CONTEXT = "context";
  public static final String PROP_NAMES = "names";
  public static final String PROP_IDS = "ids";
  private String query;
  private String type;
  private String context;
  private String[] names;
  private String[] ids;
  private final PropertyChangeSupport propertySupport;
  
  public QueryBean() {
    query = null;
    type = null;
    context = "search.jsp?query=";
    names = new String[0];
    ids = new String[0];
    propertySupport = new PropertyChangeSupport(this);
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
   * @return the names
   */
  public String[] getNames() {
    return names;
  }
  /**
   * @param names the names to set
   */
  public void setNames(String[] names) {
    String[] oldNames = this.names;
    this.names = names;
    propertySupport.firePropertyChange(PROP_NAMES, oldNames, names);
  }
  /**
   * @return the ids
   */
  public String[] getIds() {
    return ids;
  }
  /**
   * @param ids the ids to set
   */
  public void setIds(String[] ids) {
    String[] oldIds = this.ids;
    this.ids = ids;
    propertySupport.firePropertyChange(PROP_IDS, oldIds, ids);
  }
}
