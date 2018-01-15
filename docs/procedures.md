# Procedures

Procedures for working on the project.

## Task Delegation

- Every task will be a GitHub issue
	- Labeled properly
	- Assigned there
	- Comment there as needed
	- Break up bigger issues into smaller ones
- Organize issues with milestones and projects
- Each issue is a new Git Branch forked off of the dev branch
- Always do a little work

## Submitting Code

- Run tests
- Push your branch to GitHub
	- Include useful comments
- Make a pull request to dev
- Assign someone to review it

## Meetings

- Regular meetings with adviser 
- Have a once a week status meeting
- Spend some time working in Marston where other members can join

## Code style

- Comments
    - Used to describe why your code does what it does. Use proper grammar.
    - Place above the relevant lines of code
	- Use Javadoc standard comments
	- Comment classes, functions, and methods
	- Comment related blocks of code
	- Keep comments updated
	- Use TODO comments
- Line Length
    - 100 character limit
- Indention
    - Always indent for new scope
    - Indents are tabs
    - When you exceeded the line limit, create a new line and indent twice to signify continuation.
	- When breaking up a line return after commas, and before (+, -, ==, &&, etc).
```c++
if (bar == 5 || bar == 12 || bar == 98 
		|| bar == 120 || bar == 177 || var == 180)
{
	foo = 2;
}
```
- Number of lines per file
    - Try to have a maximum of around 200 lines
- Spacing
	- Separate different tasks/ideas with a blank line
	- Separate different functions/classes/methods with a blank line
	- Use spaces around math operators and Boolean expressions
```c++
// Addition
int x = 1 + 3;

// Multiplication
bool y = true || false;
```
- Naming  
	- Use descriptive names, with correctly spelled words
	- Constants in CAPS_WITH_UNDER
	- Classes in CapWords
	- Variables and methods in mixedCase
- Braces
	- Shall be placed on a new line
```c++
for (int i = 0; i < count; i++)
{
	std::cout << i;
}
```
- Write short methods
	- If over 40 lines, look into breaking it up
- Use standard Java annotations
	- Such as `@Override` or `@Depricated`
- Other
	- Group related code
	- Avoid deep nesting- Exceptions
	- Don't ignore exceptions
	- Don't catch generic exception
	- Fully qualify imports

