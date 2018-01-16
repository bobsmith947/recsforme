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
package me.recsfor.search_engine;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lkitaev
 */
@Entity
@Table(name = "QList")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QList.findAll", query = "SELECT s FROM QList s"),
    @NamedQuery(name = "QList.findByNumResults", query = "SELECT s FROM QList s WHERE s.numResults = :numResults"),
    @NamedQuery(name = "QList.findByLastQueried", query = "SELECT s FROM QList s WHERE s.lastQueried = :lastQueried")})
public class QList implements Serializable {
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Query")
    @Id
    private String query;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NumResults")
    private short numResults;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastQueried")
    @Temporal(TemporalType.DATE)
    private Date lastQueried;

    public QList() {
        this.query = null;
    }

    public QList(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public short getNumResults() {
        return numResults;
    }

    public void setNumResults(short numResults) {
        this.numResults = numResults;
    }

    public Date getLastQueried() {
        return lastQueried;
    }

    public void setLastQueried(Date lastQueried) {
        this.lastQueried = lastQueried;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (query != null ? query.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QList)) {
            return false;
        }
        QList other = (QList) object;
        return !((this.query == null && other.query != null) || (this.query != null && !this.query.equals(other.query)));
    }

    @Override
    public String toString() {
        return "me.recsfor.search_engine.Search[ query=" + query + " ]";
    }
    
}
