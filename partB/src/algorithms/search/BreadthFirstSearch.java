package algorithms.search;
import java.util.*;


/**
 * BreadthFirstSearch class that extends the Abstract ASearchingAlgorithm class.
 * this class represents the BFS searching algorithm.
 */

public class BreadthFirstSearch extends ASearchingAlgorithm{

    protected PriorityQueue<AState> openList;
    protected HashMap<String,Boolean> visited;


    /**
     * Constructor, initializes the attributes.
     */
    public BreadthFirstSearch() {
        super();
        this.name = "Breadth First Search";
        this.openList = new PriorityQueue<>();
        this.visited = new HashMap<>();
    }


    /**
     * Method which solve the problem.
     * the solution includes the path from start state to goal state.
     * @param maze: ISearchable Object, means Object which can be searched.
     * @return the solution of the searching problem.
     */
    @Override
    public Solution solve(ISearchable maze) {
        if(maze == null){
            System.out.println("cannot solve a null maze!");
            return null;
        }

        AState startState = maze.getStartState();
        AState goalState = maze.getGoalState();
        boolean found = false;
        if (startState == null || goalState == null)
            return null;

        startState.setCost(-1);
        goalState.setCost(0);
        AState currState = startState;
        ArrayList<AState> successors;

        this.openList.add(startState);
        this.visited.put(currState.toString(),true);

        while (!(openList.isEmpty()) && !found){
            currState = this.openList.remove();
            successors = maze.getAllPossibleState(currState);
            this.visited.put(currState.toString(),true);

            if (goalState.equals(currState)){
                found = true;
                break;
            }

            for (AState successor : successors){
                if(!(this.visited.containsKey(successor.toString()))){
                    this.openList.add(successor);
                    this.visited.put(successor.toString(),true);
                }
            }

            this.visitedNodes++;
        }

        return new Solution(currState);
    }

}

