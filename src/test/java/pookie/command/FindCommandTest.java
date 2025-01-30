package pookie.command;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pookie.MockUi;
import pookie.TaskList;
import pookie.model.Todo;

public class FindCommandTest {
    private MockUi mockUi;
    private TaskList taskList;
    private FindCommand findCommand;

    @BeforeEach
    public void setUp() {
        mockUi = new MockUi();
        taskList = new TaskList(new ArrayList<>());
        findCommand = new FindCommand();

        // Existing task: item 1 (todo)
        taskList.add(new Todo(false, "item 1"));
    }

    @Test
    public void testFindWithoutKeyword() throws Exception {
        String input = "find";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(1, mockUi.getMessages().size());
        assertEquals("usage: find <keyword>", mockUi.getMessages().get(0));
    }

    @Test
    public void testFindPartialMatch() throws Exception {
        String input = "find item";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(2, mockUi.getMessages().size());
        assertEquals("Here are the matching tasks in your list:", mockUi.getMessages().get(0));
        assertEquals("1. [T][ ] item 1", mockUi.getMessages().get(1));
    }

    @Test
    public void testFindExactMatch() throws Exception {
        String input = "find item 1";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(2, mockUi.getMessages().size());
        assertEquals("Here are the matching tasks in your list:", mockUi.getMessages().get(0));
        assertEquals("1. [T][ ] item 1", mockUi.getMessages().get(1));
    }

    @Test
    public void testFindCaseInsensitiveMatch() throws Exception {
        String input = "find ITEM";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(2, mockUi.getMessages().size());
        assertEquals("Here are the matching tasks in your list:", mockUi.getMessages().get(0));
        assertEquals("1. [T][ ] item 1", mockUi.getMessages().get(1));
    }

    @Test
    public void testFindNoMatch() throws Exception {
        String input = "find blah";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(1, mockUi.getMessages().size());
        assertEquals("No matching tasks found.", mockUi.getMessages().get(0));
    }

    @Test
    public void testFindWithMultipleTasksMatching() throws Exception {
        // Adding additional tasks for multiple matches
        taskList.add(new Todo(false, "item 2"));
        taskList.add(new Todo(false, "another item"));

        String input = "find item";
        findCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(4, mockUi.getMessages().size());
        assertEquals("Here are the matching tasks in your list:", mockUi.getMessages().get(0));
        assertEquals("1. [T][ ] item 1", mockUi.getMessages().get(1));
        assertEquals("2. [T][ ] item 2", mockUi.getMessages().get(2));
        assertEquals("3. [T][ ] another item", mockUi.getMessages().get(3));
    }

    @Test
    public void testFindWithEmptyTaskList() throws Exception {
        TaskList emptyTaskList = new TaskList(new ArrayList<>());
        String input = "find item";
        findCommand.execute(input, mockUi, emptyTaskList, null, true);

        assertEquals(1, mockUi.getMessages().size());
        assertEquals("No matching tasks found.", mockUi.getMessages().get(0));
    }
}