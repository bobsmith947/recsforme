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
package me.recsfor.rec;

import java.util.LinkedHashMap;

/**
 * Abstract class containing the essential fields for recommendations.
 * @author lkitaev
 */
public abstract class AbstractRec implements GenericRec {
  protected boolean request;
  protected LinkedHashMap<String, String> response;
  
  public AbstractRec() {
    request = false;
    response = null;
  }
  
  @Override
  public boolean isLike() {
    return request;
  }
  
  @Override
  public boolean isDislike() {
    return !request;
  }
}
