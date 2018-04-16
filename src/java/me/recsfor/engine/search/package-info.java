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
 * This package provides functionality to process media queries.
 * The appropriate API (OMDb or MusicBrainz) is used for generating search results and group pages.
 * When a request is been sent to <code>search.jsp</code>, a <code>QueryBean</code> is instantiated.
 * Search results will automatically link to their appropriate servlet.
 */

package me.recsfor.engine.search;
