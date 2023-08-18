# ***GUI Implementation.***

This is the implementation of the maze GUI with MVVM architecture.<br>
For this part we use **JavaFX** and implemented the app with **MVVM** architecture, that stands for: **Model, View, ViewModel**. <br>
As we go through this README file I will explain for what stands every section of the MVVM architecture. <br><br>


**Model, View, ViewModel(MVVM) architecture:**
1. **Model:** houses the logic for the program and responsible for:<br>
   * The communication to the servers that create and solve mazes.
   * Use of the algorithms.
   * Saving the maze that the user is currently playing.
   * Saving the character's position in the current maze.

<br>
   
2. **View:** is responsible for the Frontend of the Application, from the view, layers, design and all of that.

<br>

3. **ViewModel:** is located between the View and Model layers and responsible for the connection between them.
