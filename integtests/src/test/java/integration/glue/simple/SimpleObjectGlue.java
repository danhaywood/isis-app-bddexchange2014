/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package integration.glue.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import dom.simple.TodoItem;
import dom.simple.TodoItems;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import org.apache.isis.core.specsupport.scenarios.InMemoryDB;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

public class SimpleObjectGlue extends CukeGlueAbstract {

    @Given("^there are.* (\\d+) simple objects$")
    public void there_are_N_simple_objects(int n) throws Throwable {
        if(supportsMocks()) {
            checking(new Expectations() {
                {
                    allowing(service(TodoItems.class)).listAll();
                    will(returnValue(allSimpleObjects()));
                }
            });
        }
        try {
            final List<TodoItem> findAll = service(TodoItems.class).listAll();
            assertThat(findAll.size(), is(n));
            putVar("list", "all", findAll);
            
        } finally {
            assertMocksSatisfied();
        }
    }
    
    @When("^I create a new simple object$")
    public void I_create_a_new_simple_object() throws Throwable {
        if(supportsMocks()) {
            checking(new Expectations() {
                {
                    oneOf(service(TodoItems.class)).create(with(any(String.class)));
                    will(addToInMemoryDB());
                }
            });
        }
        service(TodoItems.class).create(UUID.randomUUID().toString());
    }
    
    private Action addToInMemoryDB() {
        return new Action() {
            
            @Override
            public Object invoke(Invocation invocation) throws Throwable {
                final InMemoryDB inMemoryDB = getVar("isis", "in-memory-db", InMemoryDB.class);
                final String name = (String)invocation.getParameter(0);
                final TodoItem obj = new TodoItem();
                obj.setDescription(name);
                inMemoryDB.put(TodoItem.class, name, obj);
                return obj;
            }
            
            @Override
            public void describeTo(Description description) {
                description.appendText("add to database");
            }
        };
    }

    // helper
    private List<TodoItem> allSimpleObjects() {
        final InMemoryDB inMemoryDB = getVar("isis", "in-memory-db", InMemoryDB.class);
        return inMemoryDB.findAll(TodoItem.class);
    }
}
