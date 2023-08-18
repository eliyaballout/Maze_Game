package algorithms.search;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


/**
 * DepthFirstSearch class that extends the Abstract ASearchingAlgorithm class.
 * this class represents the DFS searching algorithm.
 */

public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> openList;
    private HashMap<String,Boolean> visited;


    /**
     * Constructor, initializes the attributes.
     */
    public DepthFirstSearch() {
        super();
        this.name = "Depth First Search";
        this.openList = new Stack<>();
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
        if (maze == null) {
            System.out.println("cannot solve a null maze!");
            return null;
        }

        AState startState = maze.getStartState();
        AState goalState = maze.getGoalState();
        AState currState = startState;
        ArrayList<AState> successors;
        boolean found = false;
        if(startState == null || goalState == null)
            return null;

        startState.setCost(-1);
        goalState.setCost(0);
        this.openList.push(currState);

        while (!this.openList.isEmpty()){
            currState = this.openList.pop();
            successors = maze.getAllPossibleState(currState);

            if (goalState.equals(currState) && !found) {
                found = true;
                break;
            }

            for (AState successor : successors) {
                if (!(this.openList.contains(successor))){
                    if (successor.equals(goalState)){
                        return new Solution(successor);
                    }

                    this.openList.push(successor);
                    this.visited.put(successor.toString(), true);
                }
            }

            this.visitedNodes++;
        }

        return new Solution(currState);
    }

}

