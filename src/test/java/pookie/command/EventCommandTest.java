package pookie.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pookie.MockUi;
import pookie.TaskList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventCommandTest {
    private MockUi mockUi;
    private TaskList taskList;
    private EventCommand eventCommand;

    @BeforeEach
    public void setUp() {
        mockUi = new MockUi();
        taskList = new TaskList(new ArrayList<>());
        eventCommand = new EventCommand();
    }

    @Test
    public void testEmptyEventCommand() throws Exception {
        String input = "event";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testEventWithoutFromAndTo() throws Exception {
        String input = "event project meeting";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testEventWithOnlyFrom() throws Exception {
        String input = "event project meeting /from";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testInvalidFromDate() throws Exception {
        String input = "event project meeting /from 69";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testEventWithOnlyValidFrom() throws Exception {
        String input = "event project meeting /from 29/01/2001 1159";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testEventWithMissingTo() throws Exception {
        String input = "event project meeting /from 29/01/2001 1159 /to";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testEventWithMissingFrom() throws Exception {
        String input = "event project meeting /from /to 29/01/2001 1159";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testInvalidToDate() throws Exception {
        String input = "event project meeting /to 69";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testInvalidFormattedToDate() throws Exception {
        String input = "event project meeting /to Mon 29/01/2001 1159";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testBothDatesInvalid() throws Exception {
        String input = "event project meeting /from 69 /to 69";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.", mockUi.getMessages().get(0));
    }

    @Test
    public void testToDateBeforeFromDate() throws Exception {
        String input = "event project meeting /to Mon 29/01/2001 1159 /from 29/01/2001 1159";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: event <description> /from <start time> /to <end time>", mockUi.getMessages().get(0));
    }

    @Test
    public void testInvalidFromDateFormat() throws Exception {
        String input = "event project meeting /from Mon 29/01/2001 1159 /to 29/01/2001 1159";
        eventCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.", mockUi.getMessages().get(0));
    }
}