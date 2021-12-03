import java.util.Scanner;


public class MyAppli {

    public static void main(String[] args) {
        MyAppli.menu();
        Scanner sc = new Scanner(System.in);
        Automata a = MyAppli.ChooseMenu(sc.nextInt());
        if(a==null || !a.isInitialised()){
            System.out.println("Error");
            return;
        }
        String phrase;
        do {
            System.out.println("\n\033[1;34mPlease enter your sentence or 'toString' to print the Automata or enter 'quit' to quit\033[0m");
            Scanner sc2 = new Scanner(System.in);
            phrase = sc2.nextLine();
            if(phrase.equalsIgnoreCase("toString"))
                System.out.println(a);
            else if (phrase.equalsIgnoreCase("quit"))
                return;
            else if(a.verify(phrase))
                System.out.println("\033[0;32m✅ The sentence is a "+ a.getName());
            else
                System.out.println("\033[0;31m❌ The sentance is NOT a "+ a.getName());
        }while(true);
    }

    /**
     * menu is a function to print the menu
     */
    public static void menu(){
        System.out.println("\033[1;34mChoose an Automata with the number :\033[0m" +
                "\n1-Smiley" +
                "\n2-HH:MM" +
                "\n3-JJ/MM/AAAA" +
                "\n4-Email" +
                "\n5-Equation" +
                "\n9-Implement your Automata"
        );
    }

    /**
     * ChooseMenu is a function for choosing the Automata to read.
     * @param s : int for the choice
     * @return Automata in function of the user's choice
     */
    public static Automata ChooseMenu(int s){
        switch (s) {
            case 1 -> {
                Automata a = new Automata("SMILEY");
                a.loadAutomata("automata/automateSMILEY.txt");
                return a;
            }
            case 2 -> {
                Automata a = new Automata("HH:MM");
                a.loadAutomata("automata/automateHH:MM.txt");
                return a;
            }
            case 3 -> {
                Automata a = new Automata("JJMMAAAA");
                a.loadAutomata("automata/automateJJMMAAAA.txt");
                return a;
            }
            case 4 -> {
                Automata a = new Automata("EMAIL");
                a.loadAutomata("automata/automateEMAIL.txt");
                return a;
            }
            case 5 -> {
                Automata a = new Automata("EQUATION");
                a.loadAutomata("automata/automateEQUATION.txt");
                return a;
            }
            case 9 -> {
                System.out.println("What is your file path ?");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                Automata a = new Automata(line.substring(line.lastIndexOf("/") == -1 ? 0 : line.lastIndexOf("/")+1));
                a.loadAutomata(line.strip());
                return a;
            }
            default -> System.out.println("Invalide syntax");
        }
        return null;
    }


}
