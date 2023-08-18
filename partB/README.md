# ***Client-Server Implementation.***

This is the implementation of the maze client-server architecture.<br>
We used the **Strategy Design Pattern** in order to use different variants of an algorithm within an object and be able to switch from one algorithm to another during runtime. <br>
In addition, we used the **Decorator Design Pattern** to compress and decompress the maze in order to save it and load it whenever we want. <br><br>


The default algorithms from [partA](https://github.com/eliyaballout/Maze_Game/tree/main/partA) that is set for the game is:
* For **generating** the maze we used: **My Maze Generator** as the default.
* For **solving** the maze we used: **BFS** as the default.<br>

**You can change this default settings, continue with this README file to guide you.**

<br><br>




# Configuration

In order to change the default settings for the maze algorithms, you need to navigate to [config file](https://github.com/eliyaballout/Maze_Game/blob/main/partB/resources/config.properties), and do the following:

### Changing the **generating** algorithm:
You will change this `mazeGeneratingAlgorithm` parameter, and for now do not touch the other.<br>
There are three algorithms you can go with:
```
1. EmptyMazeGenerator
2. SimpleMazeGenerator
3. MyMazeGenerator
```

Take the algorithm that you want, copy and paste it as is in the right place. **make sure that you write the name of the algorithm as it shown above**. 
<br>

For example if you choose to take the second algorithm, the result should be like this:
```
mazeGeneratingAlgorithm = SimpleMazeGenerator
```
<br><br>


### Changing the **solving** algorithm:
You will change this `mazeSearchingAlgorithm` parameter, and for now do not touch the other.<br>
There are three algorithms you can go with:
```
1. BreadthFirstSearch
2. DepthFirstSearch
3. BestFirstSearch
```

Take the algorithm that you want, copy and paste it as is in the right place. **make sure that you write the name of the algorithm as it shown above**.
<br>

For example if you choose to take the second algorithm, the result should be like this:
```
mazeSearchingAlgorithm = DepthFirstSearch
```
<br>
