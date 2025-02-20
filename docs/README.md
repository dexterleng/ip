# Chatbot User Guide

Welcome to the Chatbot User Guide! This guide provides an overview of how to use the chatbot effectively, including its features and supported commands.

## Getting Started

1. **Run the chatbot**: Start the chatbot using the appropriate command for your setup.
2. **Interact with the chatbot**: Use text commands to add, manage, and retrieve tasks.
3. **Exit when done**: Type `bye` to close the chatbot.

## Features

- Add different types of tasks: To-Dos, Deadlines, and Events.
- List all tasks.
- Find tasks by keyword.
- Mark and unmark tasks as completed.
- Delete tasks.
- Save tasks between sessions.

## Commands

### 1. `todo <description>`
Adds a to-do task.

**Example:**
```
todo Buy groceries
```

### 2. `deadline <description> /by <deadline>`
Adds a deadline task with a due date.

**Example:**
```
deadline Submit report /by 23/02/2025 2359
```

### 3. `event <description> /from <start time> /to <end time>`
Adds an event with a start and end time.

**Example:**
```
event Karaoke /from 23/02/2025 0000 /to 23/02/2025 2359
```

### 4. `list`
Displays all tasks.

**Example:**
```
list
```

### 5. `find <keyword>`
Finds tasks containing the given keyword.

**Example:**
```
find report
```

### 6. `mark <task number>`
Marks a task as completed.

**Example:**
```
mark 2
```

### 7. `unmark <task number>`
Marks a task as not completed.

**Example:**
```
unmark 2
```

### 8. `delete <task number>`
Deletes a task.

**Example:**
```
delete 3
```

### 9. `bye`
Exits the chatbot.

**Example:**
```
bye
```
