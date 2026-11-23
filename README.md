<H1>
TodoFX
</H1>
I made a to-do list application in JavaFX, using the Maven archetype and MVC architecture. The user can add a task, edit its content (title, description, priority, completed status) by double clicking a task
or delete it. <br>
The tasks are sorted by completed status.<br>
Tasks are stored in a .json file. <br>
<br>
Dependencies can be found in the pom.xml file, under "dependencies".

<H2>Running the app on Windows</H2> <br>
In order to launch the app, either:<br><br>
1) download the latest 'TodoFx.zip' file from the Releases page (No Java installation required), extract the contents to a folder and run the 'TodoFx.exe' file. <br><br>
2) download the source code (can also be done by cloning the repository), opening in IntelliJ IDEA, locating src/main/java/fi/jyu/ohj2/ipetresx/todo and running Main.java
(requires JDK 25 or newer). If using Maven, can be ran directly from terminal : './mvnw javafx:run'.

## Testing <br>
Tests can be found under src/test/java in the 'TaskCollectionTest.java' file. Running the main class will also run the tests. (Side note: 2 tests are expected to fail without
manually modifying the wait function in the 'TaskCollection' class)
