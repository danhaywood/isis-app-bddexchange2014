/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dom.simple;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@ObjectType("SIMPLE")
@Bookmarkable
public class TodoItem implements Comparable<TodoItem> {

    //region > description (property)

    private String description;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    //endregion

    //region > complete (property)
    private boolean complete;

    @MemberOrder(sequence="2")
    public boolean getComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }
    //endregion

    //region > completed (action)
    @MemberOrder(sequence = "1", name="complete")
    public TodoItem completed() {
        setComplete(true);
        return this;
    }

    public String disableCompleted() {
        return getComplete()? "Already completed": null;
    }
    //endregion


    //region > compareTo

    @Override
    public int compareTo(TodoItem other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
