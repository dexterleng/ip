import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineCommandTest {
    private MockUi mockUi;
    private TaskList taskList;
    private DeadlineCommand deadlineCommand;

    @BeforeEach
    public void setUp() {
        mockUi = new MockUi();
        taskList = new TaskList(new ArrayList<>());
        deadlineCommand = new DeadlineCommand();
    }

    @Test
    public void testDeadlineWithoutBy() throws Exception {
        String input = "deadline return book";
        deadlineCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: deadline <description> /by <deadline>", mockUi.getMessages().get(0));
    }

    @Test
    public void testDeadlineWithEmptyBy() throws Exception {
        String input = "deadline return book /by";
        deadlineCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: deadline <description> /by <deadline>", mockUi.getMessages().get(0));
    }

    @Test
    public void testDeadlineWithInvalidDate() throws Exception {
        String input = "deadline /by 69";
        deadlineCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("usage: deadline <description> /by <deadline>", mockUi.getMessages().get(0));
    }

    @Test
    public void testDeadlineWithValidDate() throws Exception {
        String input = "deadline return book /by 29/01/2001 1159";
        deadlineCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(1, taskList.size());
        Deadline deadline = (Deadline) taskList.get(0);
        assertEquals("return book", deadline.getDescription());
        assertEquals("29/1/2001 1159", deadline.getDeadline().format(Pookie.INPUT_FORMATTER));
        assertEquals(3, mockUi.getMessages().size());
    }

    @Test
    public void testDeadlineWithInvalidDateFormat() throws Exception {
        String input = "deadline return book /by Sunday";
        deadlineCommand.execute(input, mockUi, taskList, null, true);

        assertEquals(0, taskList.size());
        assertEquals("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.", mockUi.getMessages().get(0));
    }
}