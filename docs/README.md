# Janet User Guide

Welcome to the Janet user guide! Janet is a desktop application that helps you track your everyday tasks.

![Janet product screenshot.](/docs/Ui.png)

This user guide provides in-depth documentation on how you can install, configure, and set up your own Janet program.

## Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)

<a name="quick-start"></a>
## Quick Start
1. **Ensure that Java `25` or later is installed on your computer**.
2. Download the latest `.jar` file from here
3. Copy the file to the folder you want to use as the home folder for your Janet application.
4. Open a terminal, cd to the folder containing the `.jar` file, and run `java -jar janet.jar`  
A GUI similar to the one above should appear in a few seconds. Note how the app contains some sample data.
5. Type a command in the command box and press Enter to execute it. For example, type `list` and press Enter to list current tasks.

<a name="features"></a>
## Features
> [!IMPORTANT] 
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.  
> * Janet is **case-sensitive**. Commands in a different case won't be recognized.
> * Extra parameters for commands that take no parameters, like `list`, will be ignored.

### Listing tasks: `task`
Displays the list of tasks, if any, currently stored by Janet.

### Adding Tasks
> Adding tasks is simple; all tasks have the same base format for doing so.   
Format: `TASK_TYPE TASK_LABEL`
> * Adds a task with a `TASK_LABEL` description.

#### Adding a todo task: `todo`
Adds a task that has no deadline or period.  

Format: `todo TASK_LABEL`

*Examples*:  
* `todo eat`
* `todo meet Sarah`

#### Adding a deadline task: `deadline`
Adds a task that has a deadline.

#### Adding an event task: `event`
Adds a task that lasts for a specified period.

### Deleting a task: `delete`
Deletes a task, if it exists, based on their current sorted position in the list.

*Example*:  
`delete 1` Deletes the first item on the list.

### Finding tasks: `find`
Finds tasks, if any, that match the given phrase.

### Marking a task: `mark`
Marks a task, if it exists, as completed based on their current sorted position in the list.

### Sorting tasks: `sort`
Sorts tasks based on the provided sort type.

*Examples:*  
`sort default` Sorts tasks by their insertion order in the list.  
`sort label` Sorts tasks by alphabetical order based on their description.

<a name="faq-start"></a>
## FAQ
**Q**: What happens to my tasks after I close Janet?  
**A**: Janet stores your tasks in a `.txt` file. You can find this in `/data/tasks.txt` in your app directory; Janet saves to this file every time it's modified. **When you reopen Janet, these tasks are reloaded**.
> [!TIP]
> You can manually edit the `tasks.txt` file to add in new tasks.
**Make sure to format your edits accordingly, or Janet will not load your tasks in storage.**