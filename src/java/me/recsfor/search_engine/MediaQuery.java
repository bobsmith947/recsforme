package me.recsfor.search_engine;

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

/**
 *
 * @author lkitaev
 */
public class MediaQuery {
    private String query;
    public MediaQuery() {
        this.query = null;
    }
    public String getQuery() {
        return this.query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
}
