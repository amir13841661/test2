package snake;
import java.util.Scanner;

// import org.jline.builtins.ScreenTerminal;
// import org.jline.terminal.Terminal;
// import org.jline.terminal.TerminalBuilder;
// import org.jline.jansi.AnsiConsole;

// import org.jline.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // public static Terminal terminal;
    // public static ScreenTerminal screenTerminal;
    
    public static void main(String[] args){
        // AnsiConsole.systemInstall();
        int menuChoice;
        Scanner input=new Scanner(System.in);
        Difficulity difficulity =Difficulity.EASY;
        while(true){
            System.out.print("1.start game\n2.change difficulty\n3.exit\n");
            menuChoice=ForceNumericInput.force();
            
            switch (menuChoice) {
                case 1:
                    Game.startGame();
                    break;

                case 2:
                    while(menuChoice!=4){
                        System.out.println("1.easy\n2.medium\n3.hard\n4.back");
                        System.out.print("enter difficulity:");
                        menuChoice=ForceNumericInput.force();
                        switch (menuChoice) {
                            case 1:
                                Game.setDifficulity(Difficulity.EASY);
                                
                                break;

                            case 2:
                                Game.setDifficulity(Difficulity.MEDIUM);
                                break;
                            case 3:
                                Game.setDifficulity(Difficulity.HARD);
                        }
                        System.out.println("difficulity changed to "+Game.getDifficulity());

                    }
                    break;
                    
                case 3:
                    return;

            }
        }
    }
}