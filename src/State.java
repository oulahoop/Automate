import java.util.*;

public class State {
    private String name;
    private HashMap<Character, State> transition;

    /**
     * Constructor with name only
     * @param name name of the State
     */
    public State(String name) {
        this.name = name;
        this.transition = new HashMap<>();
    }

    /**
     * Adding a State to the HashMap transition
     * @param c character to give to "access" to the State state
     * @param state the State accessing by the character c
     */
    public void addState(Character c, State state) {
        transition.put(c, state);
    }

    /**
     * Next state in function of the char given
     * @param c char to get next State
     * @return next State or null if not next State with the char
     */
    public State nextState(Character c) {
        return transition.get(c);
    }

    /**
     * Getter for transition
     * @return HashMap<Character,State> of all transition for the State
     */
    public HashMap<Character, State> getTransition() {
        return transition;
    }

    /**
     * toString return the name of the State
     * @return String on the name of the State
     */
    public String toString() {
        return this.name;
    }
}


