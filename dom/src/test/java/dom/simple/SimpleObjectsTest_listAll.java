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
package dom.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.google.common.collect.Lists;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

public class SimpleObjectsTest_listAll {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    private DomainObjectContainer mockContainer;
    
    private TodoItems todoItems;

    @Before
    public void setUp() throws Exception {
        todoItems = new TodoItems();
        todoItems.container = mockContainer;
    }
    
    @Test
    public void happyCase() throws Exception {
        
        // given
        final List<TodoItem> all = Lists.newArrayList();
        
        context.checking(new Expectations() {
            {
                oneOf(mockContainer).allInstances(TodoItem.class);
                will(returnValue(all));
            }
        });
        
        // when
        final List<TodoItem> list = todoItems.listAll();
        
        // then
        assertThat(list, is(all));
    }

}
