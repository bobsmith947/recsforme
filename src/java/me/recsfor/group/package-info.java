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
 * This package provides functionality to dynamically generate group pages using <code>HTTP GET</code> and <code>POST</code> requests.
 * The servlets are registered in the <code>web.xml</code> file, and do no have any initialization parameters.
 * Query strings are to be formatted with an <code>id</code> parameter set to the group ID.
 * This process can be initiated from the frontend by selecting a result on the <code>search.jsp</code> page.
 */

package me.recsfor.group;