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

--statements are made using SQL Server (Transact-SQL) syntax 

create table query_log
(
	id INT IDENTITY(1,1) not null primary key,
	query VARCHAR(100) not null,
	qtype VARCHAR(6) not null
);

create table users
(
	id INT IDENTITY(1,1) not null primary key,
	uname VARCHAR(36) not null,
	pw CHAR(64) not null,
        salt CHAR(32) not null,
	joined DATE not null,
	sex VARCHAR(6),
	dob DATE,
	email VARCHAR(254)
);

create unique index users_idx
  on users (uname,pw,salt);

create table user_likes
(
        id INT IDENTITY(1,1) not null primary key,
        uid INT foreign key references users(id),
        items VARCHAR(max)
);

create table user_dislikes
(
        id INT IDENTITY(1,1) not null primary key,
        uid INT not null foreign key references users(id),
        items VARCHAR(max)
);