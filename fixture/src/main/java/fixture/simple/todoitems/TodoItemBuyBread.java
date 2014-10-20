package fixture.simple.todoitems;

import dom.simple.TodoItems;
import fixture.simple.TodoItemsTearDownFixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class TodoItemBuyBread extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
        executeChild(new TodoItemsTearDownFixture(), executionContext);

        executionContext.add(this, todoItems.create("Buy bread"));
    }

    @javax.inject.Inject
    private TodoItems todoItems;

}
