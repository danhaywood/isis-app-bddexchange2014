package fixture.simple.scenario;

import fixture.simple.TodoItemsTearDownFixture;
import fixture.simple.todoitems.TodoItemBuyBread;
import fixture.simple.todoitems.TodoItemBuyMilk;
import fixture.simple.todoitems.TodoItemPickUpLaundry;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SomeIncompleteTodoItems extends FixtureScript {

    public SomeIncompleteTodoItems() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executeChild(new TodoItemsTearDownFixture(), executionContext);

        // set up scenario
        executeChild(new TodoItemBuyBread(), executionContext);
        executeChild(new TodoItemBuyMilk(), executionContext);
        executeChild(new TodoItemPickUpLaundry(), executionContext);

    }
}
