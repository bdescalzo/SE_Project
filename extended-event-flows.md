# Extended Event Flows for TxikI/A


## Use Case 1: Ask questions to AI

**Primary Actor:** User  
**Secondary Actors:** LLM AI
**Preconditions:**
-None

### Main Flow:
Event Flow for "Ask Questions to AI" (Actor: User):
1-User logs in.
2-User selects a project they want to work on from their list of saved projects.
3-User opens the AI chat within the selected project.
4-User types a project-related question and submits it to the AI.
5-System stores the question in the project database.
6-System forwards the question to the LLM AI for processing.
7-LLM AI generates a response based on the question and sends it back to the system.
8-System displays the AI's response in the chat interface.
9-The response of the LLM AI is saved in the project database.
8-User reviews the answer and can either:
  - Ask a follow-up question, returning to step 3.
  - Exit the chat or try to do a different action. In that case, go to the next step. 
9-Save the conversation for future reference.

### Alternative Flows:
**A1: Non project-related question**
- At step 6, if the question typed by the user un step 4 is not project-related:
  1. LLM AI detects the question is not project-related and lets it know to the system.
  2. The system notify the user that the question typed is not project-related.
  3. Remove the information stored in the project database about the question asked by the user.
  4. The process go back to step 4.


## Use Case 2: Generate Quiz

**Primary Actor:** LLM AI
**Secondary Actors:** User
**Preconditions:**
-There is already some information saved about the project.

### Main Flow:
Event Flow for "Generate Quiz" (Actor: LLM AI)
1-User logs in.
2-User selects a project from their saved projects.
3-User chooses the "Generate Quiz" option from the project menu.
4-System requests the LLM AI to generate quiz questions related to the project.
5-LLM AI analyzes the project's stored information:
  -Past interactions.
  -Solved problems and quizzes.
  -Topics covered.
6-LLM AI generates a set of quiz-type questions with possible answer choices (multiple choice) and only a correct one.
7-System presents the quiz to the user, displaying the generated questions.
8-The information about the displayed quiz is stored in the project database.
