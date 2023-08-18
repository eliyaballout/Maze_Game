package algorithms.search;
import java.util.ArrayList;


/**
 * ISearchable Interface to Implement.
 * represents Searchable Objects.
 */

public interface ISearchable {

    /**
     * getter for the start state.
     * @return start state.
     */
    AState getStartState();

    /**
     * getter for the goal state.
     * @return goal state.
     */
    AState getGoalState();

    /**
     * getter for all the successors of a given state.
     * @param state: the given state.
     * @return array of all the successors.
     */
    ArrayList<AState> getAllPossibleState(AState state);

}
