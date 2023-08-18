package algorithms.search;


/**
 * Abstract class ASearchingAlgorithm.
 * the class implements the ISearchingAlgorithm Interface,
 * and represents abstract class of Searching Algorithm
 */

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int visitedNodes;
    protected String name;


    /**
     * Constructor
     * Initialize the attribute visitedNodes to 0, and the name to null
     */
    public ASearchingAlgorithm(){
        this.visitedNodes = 0;
        this.name = null;
    }


    /**
     * Abstract Method which solve the problem.
     * @param maze: ISearchable Object, means Object which can be searched.
     * @return the solution of the searching problem.
     */
    @Override
    public abstract Solution solve(ISearchable maze);


    /**
     * @return number of evaluated nodes
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return this.visitedNodes;
    }


    /**
     *
     * @return the name of the searching algorithm.
     */
    @Override
    public String getName() {
        return this.name;
    }

}
