package algorithms.search;
import java.util.*;


/**
 * BestFirstSearch class that extends the BreadthFirstSearch class.
 * this class is represents a Best First Search algorithm, it needs to find the best solution of the problem.
 */

public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * Constructor, initializes the attributes.
     */
    public BestFirstSearch() {
        super();
        this.name = "Best First Search";
        this.openList = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(AState o1, AState o2) {
                if (o1 != null && o2 != null)
                    return o1.cost - o2.cost;

                return -2;
            }
        });

    }

}



