@SomeCompleteAndSomeIncompleteTodoItems
Feature: Can complete todo items that have not yet been completefd

  @integration
  Scenario: Can complete a todo item that is not yet complete
    Given an incomplete todo item
    When  I complete the todo item
    Then  then it is completed.

  @integration
  Scenario: Can complete a todo item that is not yet complete
    Given a complete todo item
    When  I attempt to complete the todo item
    Then  the message advises me that the todo item is already completed.

