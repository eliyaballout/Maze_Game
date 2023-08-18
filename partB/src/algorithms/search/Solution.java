package algorithms.search;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Solution class.
 * this class represents the solution for the searching problem.
 */

public class Solution implements Serializable {

    private ArrayList<AState> solutionPath;


    /**
     * Constructor, takes a goal state and restore the solution path.
     * @param goalState: gaol state to restore the solution from.
     */
    public Solution(AState goalState){
        this.solutionPath = new ArrayList<>();
        if (goalState != null){
            Stack<AState> stateStack = new Stack<>();
            AState state = goalState;
            while (state != null){
                stateStack.push(state);
                state = state.getCameFrom();
            }

            while (!stateStack.empty())
                solutionPath.add(stateStack.pop());
        }

    }


    /**
     * getter for the solution path.
     * @return array of the solution path.
     */
    public ArrayList<AState> getSolutionPath(){
        return this.solutionPath;
    }

}
