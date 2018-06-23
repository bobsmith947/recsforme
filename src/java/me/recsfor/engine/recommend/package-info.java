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
 * This package provides functionality for generating recommendations.
 * Recommendations are primarily based on a user's similarity to other users.
 * An item in a similar user's list will be recommended if the user receiving recommendations does not already have it.
 * Recommendations are stored in a session bean, so they are not continuously regenerated.
 */

package me.recsfor.engine.recommend;
