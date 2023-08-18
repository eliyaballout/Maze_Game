package algorithms.search;
import algorithms.mazeGenerators.Position;
import java.io.Serializable;


/**
 * Abstract class AState that implements Comparable.
 * the class represents an Object of State in searching problem.
 */

public abstract class AState implements Comparable, Serializable {

    protected AState cameFrom;
    protected int cost;
    protected String stateName;
    protected int AmountOfPreviousStates;


    /**
     * Constructor, it gets the previous state and the name and initializes the attributes.
     * @param cameFrom: the previous state of the current state.
     * @param name: the name of the state.
     */
    public AState(AState cameFrom, String name){
        if (name != null) {
            this.stateName = name;
            this.cameFrom = cameFrom;
        }
        else
            this.stateName = "";

        if(cameFrom != null)
            AmountOfPreviousStates = cameFrom.AmountOfPreviousStates + 1;
        else
            AmountOfPreviousStates = 0;
    }


    /**
     * Abstract method.
     * overriding the 'equals' method, and checks if the parameter obj is the same obj as the current one(this).
     * @param obj: object to compare.
     * @return true if they are the same object.
     */
    @Override
    public abstract boolean equals(Object obj);


    /**
     * Abstract method.
     * overriding the 'compareTo' method.
     * @param obj: the object to be compared.
     * @return a number that represents who is bigger, by default positive number means the first is bigger, negative means the second is bigger, and 0 means they are equal.
     */
    @Override
    public abstract int compareTo(Object obj);


    /**
     * getter for the position.
     * @return the position.
     */
    public abstract Position getPosition();


    /**
     * overriding the 'hashCode' method.
     * @return the hash code of the string
     */
    @Override
    public int hashCode() {
        return this.stateName != null ? this.stateName.hashCode() : 0;
    }


    /**
     * getter for the previous state.
     * @return the previous state.
     */
    public AState getCameFrom(){
        return this.cameFrom;
    }


    /**
     * getter for the cost.
     * @return the cost.
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * getter for the state name.
     * @return state name.
     */
    public String getStateName(){
        return this.stateName;
    }


    /**
     * setter for the previous state
     * @param cf: the new previous state.
     */
    public void setCameFrom(AState cf){
        if (cf != null)
            this.cameFrom = cf;
    }


    /**
     * setter for the cost.
     * @param c: the new cost.
     */
    public void setCost(int c){
        this.cost = c;
    }


    /**
     * overriding the 'toString' method.
     * @return the state name.
     */
    @Override
    public String toString() {
        return this.stateName;
    }

}