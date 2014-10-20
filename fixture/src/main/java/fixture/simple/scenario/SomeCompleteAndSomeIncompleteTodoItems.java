package fixture.simple.scenario;

import dom.simple.TodoItem;
import dom.simple.TodoItems;

import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SomeCompleteAndSomeIncompleteTodoItems extends FixtureScript {

    public SomeCompleteAndSomeIncompleteTodoItems() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executeChild(new SomeIncompleteTodoItems(), executionContext);

        completed("Buy milk");
        completed("Buy bread");
    }

    private void completed(String desc) {
        final List<TodoItem> items = todoItems.listAll();
        for (TodoItem item : items) {
            if(item.getDescription().contains(desc)) {
                item.completed();
            }
        }
    }

    @Inject
    private TodoItems todoItems;
}
