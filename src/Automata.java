import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Automata {
    String name;
    Set<State> S;
    Set<Character> A;
    State S0;
    Set<State> SF;

    /**
     * Constructor of Automata. To load the Automata, use loadAutomata.
     * @param name Automata's name
     */
    public Automata(String name){
        S = new HashSet<>();
        A = new TreeSet<Character>();
        SF = new HashSet<>();
        S0 = null;
        this.name = name;
    }

    /**
     * Verify if a sentane respect the Automata.
     * @param sentance the sentance to verify
     * @return true if respect the Automata, false if not
     */
    public boolean verify(String sentance) {
        State courrant = S0;
        int compteur = 0;
        while(S.contains(courrant) && sentance.length()>compteur){
            char c = sentance.charAt(compteur);
            courrant = courrant.nextState(c);
            compteur++;
        }
        return SF.contains(courrant) && compteur == sentance.length();
    }

    /**
     * Adding a state to all the state of the Automata
     * @param s State to add
     */
    public void addState(State s){
        S.add(s);
    }

    /**
     * Adding a char to all the character of the Automata
     * @param c char to add
     */
    public void addCharacter(char c){
        A.add(c);
    }


    /**
     * Adding a transition in s1 to s2 with char c
     * @param stateName1 Name of the state you want to add transition
     * @param c char who give access to stateName2
     * @param stateName2 Name of the state you want to access with char c
     */
    public void addTransition(String stateName1, char c, String stateName2){
        State etat1 = getStateByName(stateName1), etat2 = getStateByName(stateName2);
        assert etat1 != null;
        assert etat2 != null;
        etat1.addState(c,etat2);
    }

    /**
     * Set the initial State
     * @param nameState name of the State
     */
    public void setInitial(String nameState) {
        S0 = getStateByName(nameState);
    }

    /**
     * Adding a final state with his name
     * @param nameState name of the State
     */
    public void addFinal(String nameState) {
        SF.add(getStateByName(nameState));
    }


    /**
     * Get a state by his name
     * @param stateName name of the state
     * @return the State if it's in the Automata, null if not
     */
    private State getStateByName(String stateName){
        for(State state : S){
            if(state.toString().equalsIgnoreCase(stateName)) return state;
        }
        return null;
    }

    /**
     * Getter for the name of the Automata
     * @return the name of the Automata
     */
    public String getName() {
        return this.name;
    }

    /**
     * Load an Automata with a filename. The automata must respect the format explain in ExplainationAutomataFile.txt
     * @param filename the filename of your automata
     */
    public void loadAutomata(String filename){
        String line;
        int compt = 0;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null){
                switch (compt) {
                    //First line, all the state
                    case 0 -> {
                        String[] tabAllEtat = line.split(" ");
                        for (String nameState : tabAllEtat)
                            addState(new State(nameState));
                        compt++;
                    }
                    //Second line, all the char
                    case 1 -> {
                        String tabAllChar = line.split(" ")[0];
                        for (int i = 0; i < tabAllChar.length(); i++)
                            addCharacter(tabAllChar.charAt(i));
                        compt++;
                    }
                    //Third line, the initial state
                    case 2 -> {
                        setInitial(line);
                        compt++;
                    }
                    //Four line, final(s) state(s)
                    case 3 -> {
                        String[] allFinalState = line.split(" ");
                        for (String nameState : allFinalState)
                            addFinal(nameState);
                        compt++;
                    }
                    //Fifth and more line, all the transition
                    case 4 -> {
                        line = line.trim();
                        int firstIndex = line.indexOf(" ");
                        int lastIndex = line.lastIndexOf(" ");
                        String nameState1 = line.substring(0, firstIndex);
                        String characters = line.substring(firstIndex+1, lastIndex);
                        String nameState2 = line.substring(lastIndex+1);
                        for(char c : characters.toCharArray())
                            addTransition(nameState1, c, nameState2);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("File problem.");
        }
    }



    /**
     * toString for Automata :
     * the format is : State1 -> {State2[charToAcces1,charToAcces2..]}
     * State1 with the charToAcces acces to State2.
     * @return String of the Automata
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(State s : S) {
            //reverse the map to print easier
            Map<State, ArrayList<Character>> reverseMap = new HashMap<>();
            for (Map.Entry<Character, State> entry : s.getTransition().entrySet()) {
                if (!reverseMap.containsKey(entry.getValue())) {
                    reverseMap.put(entry.getValue(), new ArrayList<>());
                }
                ArrayList<Character> keys = reverseMap.get(entry.getValue());
                keys.add(entry.getKey());
                reverseMap.put(entry.getValue(), keys);
            }
            sb.append(s).append("->").append(reverseMap).append("\n");
        }
        return sb.toString();
    }

    /**
     * Function to verify if an Automata is initialised
     * @return true if it's initialised, false if not
     */
    public boolean isInitialised() {
        return (S.size() != 0) && (S0 != null) && (SF.size() != 0);
    }
}
