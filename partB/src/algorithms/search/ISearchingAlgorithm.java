package algorithms.search;


/**
 * ISearchingAlgorithm Interface to Implement.
 * define the methods that every Searching Algorithm should have.
 */

public interface ISearchingAlgorithm {

    /**
     * Method which solve the problem.
     * the solution includes the path from start state to goal state.
     * @param search: ISearchable Object, means Object which can be searched.
     * @return the solution of the searching problem.
     */
    Solution solve(ISearchable search);

    /**
     * getter for the number of nodes that evaluated during searching for solution
     * @return number of evaluated nodes.
     */
    int getNumberOfNodesEvaluated();

    /**
     * getter for the searcher algorithm name.
     * @return algorithm name.
     */
    String getName();

}
