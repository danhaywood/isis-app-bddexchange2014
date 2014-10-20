package integration.glue.todoitem;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dom.simple.TodoItem;
import dom.simple.TodoItems;
import fixture.simple.scenario.SomeCompleteAndSomeIncompleteTodoItems;

import java.util.List;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TodoItemGlue extends CukeGlueAbstract {

    @Before(value={"@integration", "@SomeCompleteAndSomeIncompleteTodoItems"}, order=20000)
    public void integrationFixtures() throws Throwable {
        scenarioExecution().install(new SomeCompleteAndSomeIncompleteTodoItems());
    }

    @Given("^an incomplete todo item$")
    public void an_incomplete_todo_item() throws Throwable {
        final List<TodoItem> items = scenarioExecution().service(TodoItems.class).listAll();
        for (TodoItem item : items) {
            if(!item.getComplete()) {
                scenarioExecution().putVar("todo-item", "incomplete", item);
                return;
            }
        }
        throw new RuntimeException("Could not locate an incomplete todo item");
    }

    @When("^I complete the todo item$")
    public void I_complete_the_todo_item() throws Throwable {
        final TodoItem todoItem = scenarioExecution().getVar("todo-item", "incomplete", TodoItem.class);
        wrap(todoItem).completed();
        scenarioExecution().removeVar("todo-item", "incomplete");
        scenarioExecution().putVar("todo-item", "complete", todoItem);
    }

    @Then("^then it is completed.$")
    public void then_it_is_completed() throws Throwable {
        final TodoItem todoItem = scenarioExecution().getVar("todo-item", "complete", TodoItem.class);

        assertThat(todoItem.getComplete(), is(true));
    }

}
