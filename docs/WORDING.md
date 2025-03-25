# Txik/IA Intelligent Math Helper - Requirements Analysis

1. A user that enters the application needs to login on application in order to use it. If they don't have an account to login yet, they should sign up. Each user has a combination of an username and password. Once the user has logged in, they can log out and identify again with the same account or create another one whenever they want.

2. A registered user wants to work in their math projects, which are saved with several information in a projects database.

3. For each project, the user can ask questions related to this project to the LLM AI of our application, so a chat with it is open. The user can review the previous questions they have asked the AI before. The AI has three different capabilities: generating a written explanation, generating function plots and generating SageMath scripts to complement the explanation. If the user is asking for the question or quiz generator, the LLM has the capability to redirect them there, and start the quizzing/question-making process.

4. Another functionality of the projects is a generator of quizzes, where the application makes a quiz (a set of questions related to the project, each with four possible answers). Once the user has finished the quiz, it will be evaluated and they will receive some feedback.

5. The last functionality for projects is a question generator, where problem statements related to the project that is being worked on are generated. The user should write an elaborate answer, preferably with clear and concise explanations. The application should evaluate user's answer and give feedback to them, explaining what they did right in their response and what they did wrong, or if they gave a wrong answer (in this case, the application corrects the user's answer and explains what should be the correct solution).

6. The user has the chance of creating a new project, deleting an existing one, modify the name of a project and open one of their previous projects. For this last functionality, the user might want to access any of the project they has worked on whenever they want, so all the information about the projects should be stored. This information includes the questions previously asked to the IA chat, the quizzes done by the user and the problems user has previosly been asked by the application. The solutions and feedback given for these quizs and elaborated questions should also be stored.
