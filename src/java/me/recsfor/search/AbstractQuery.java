/*
 * Copyright 2018 lkitaev.
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

/**
 *
 * @author lkitaev
 */
public abstract class AbstractQuery implements GenericQuery {
    protected String query;

    /**
     * @return the query
     */
    @Override
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Performs a search using the defined client and instance query.
     */
    protected abstract void search();
}