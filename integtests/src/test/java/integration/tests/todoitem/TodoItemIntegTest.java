package integration.tests.todoitem;

import dom.simple.TodoItem;
import dom.simple.TodoItems;
import fixture.simple.scenario.SomeCompleteAndSomeIncompleteTodoItems;
import integration.tests.SimpleAppIntegTest;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TodoItemIntegTest extends SimpleAppIntegTest {

    FixtureScript fixtureScript;

    public static class Completed extends TodoItemIntegTest {

        @Before
        public void setUpData() throws Exception {
            fixtureScript = new SomeCompleteAndSomeIncompleteTodoItems();
            fixtureScripts.runFixtureScript(fixtureScript, null);
        }

        @Test
        public void happyCase_ifNotYetComplete() throws Exception {
            // given
            final TodoItem item = fixtureScript.lookup("some-complete-and-some-incomplete-todo-items/some-incomplete-todo-items/todo-item-pick-up-laundry/item-1", TodoItem.class);
            final TodoItem wrappedItem = wrapperFactory.wrap(item);

            assertThat(wrappedItem.getComplete(), is(false));

            // when
            wrappedItem.completed();

            // then
            assertThat(wrappedItem.getComplete(), is(true));
        }

        @Test
        public void sadCase_whenAlreadyComplete() throws Exception {
            // given
            final TodoItem item = fixtureScript.lookup("some-complete-and-some-incomplete-todo-items/some-incomplete-todo-items/todo-item-buy-bread/item-1", TodoItem.class);
            final TodoItem wrappedItem = wrapperFactory.wrap(item);

            assertThat(wrappedItem.getComplete(), is(true));

            // then
            expectedExceptions.expect(DisabledException.class);

            // when
            wrappedItem.completed();
        }


    }

    //region > injected services

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    TodoItems todoItems;

    @Inject
    WrapperFactory wrapperFactory;

    @Inject
    FixtureScripts fixtureScripts;

    //endregion
}