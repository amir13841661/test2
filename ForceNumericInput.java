package snake;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * forces the user to enter a number
 */
public class ForceNumericInput {
    public static int force(){
        boolean isValid=false;
        int number=0;
        Scanner input=new Scanner(System.in);
        while(!isValid){
            try{
                number=input.nextInt();
                isValid=true;
            }catch (InputMismatchException e){
                System.out.print("Invalid input.please enter a number:");
                input.next();
            }
        }
        return number;

    }
}