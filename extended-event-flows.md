# Extended Event Flows for TxikI/A


## Use Case 1: Talk With AI

**Primary Actor:** Logged User, LLM AI  
**Preconditions:**
-Logged User has valid credentials.
-Logged User has created at least one project.

### Main Flow:
Event Flow for "Ask Questions to AI" (Actor: User, LLM AI):
1. User logs in the system.
2. Logged User selects a project they want to work on.
3- Logged User opens the AI chat within the selected project.
4. Logged User types a project-related question and submits it to the AI.
5. System stores the question in the project database.
6. System forwards the question to the LLM AI for processing.
7. LLM AI generates a response based on the question and sends it back to the system.
8. System displays the AI's response in the chat interface.
9. The response of the LLM AI is saved in the project database.
10. Logged User reviews the answer and can either:
  - Ask a follow-up question, returning to step 3.
  - Exit the chat or try to do a different action. In that case, go to the next step. 
11. Save the conversation for future reference.

### Alternative Flows:
**A1: Non project-related question**
- At step 6, if the question typed by the user un step 4 is not project-related:
  1. LLM AI detects the question is not project-related and lets it know to the system.
  2. The system notify the user that the question typed is not project-related.
  3. Remove the information stored in the project database about the question asked by the logged user.
  4. The process go back to step 4.


## Use Case 2: Generate Quiz

**Primary Actor:** LLM AI, Logged User
**Preconditions:**
-Logged User has valid credentials.
-Logged User has created at least one project.

### Main Flow:
Event Flow for "Generate Quiz" (Actor: LLM AI, Logged User):
1. User logs in the system.
2. Logged User selects a project they want to work on.
3. Logged User selects the "Generate Quiz" option from the project menu.
4. System requests the LLM AI to generate quiz questions related to the project.
5. LLM AI analyzes the project's stored information:
  -Past interactions.
  -Solved problems and quizzes.
  -Topics covered.
6. LLM AI generates a set of quiz-type questions with possible answer choices (multiple choice) and only a correct one.
7. System presents the quiz to the user, displaying the generated questions.
8. The information about the displayed quiz is stored in the project database.

### Alternative Flows:
**A1: No information about the project**
- At step 4, if there is no information saved avout the project:
  1. The system detects there is no information related about the project in the database.
  2. The system notify the Logged User that some information related to the project is needed to do the operation.
  3. Stop the process.



## Use Case 3: Login

**Primary Actor:** User

### Main Flow:
Event Flow for "Login" (Actor: User):
1. System asks User for username and password
2. User provides username and password.
3. System checks data correspond to existing user.
4. System authenticates User.
5. System informs Member that access is granted.
6. User becomes Logged User.

### Alternative Flows:
**A1: Not valid user**
- At step 3, if the username doesn't correspond to existing user or username and password don't match:
  1. The system detects the authentication error.
  2. The system notifies User about the authentication error.
  3. Go back to step 1.



## Use Case 4: Delete Project

**Primary Actor:** Logged User

### Main Flow:
Event Flow for "Delete Project" (Actor: User):
1. System warns Logged User about the risks of deleting the project data
2. System asks Logged User for confirmation of deletion
3. System deleted stored project from the database. 
4. System informs Logged User of successful deletion.
5. System goes back to project selection menu and refreshes list.


### Alternative Flows:
**A1: Logged User confirmation not granded**
- At step 2, if Logged User doesn't accept deletion of project:
  1. The deletion prompt is closed.
  2. System returns to previous state.

**A2: Error in deletion**
- At step 3, if System fails to delete the project (due to an error when accessing or modifying the database):
  1. Logged User is warned about the error and prompted to try again.
  2. System returns to previous state.
