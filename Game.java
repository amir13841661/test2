package snake;
import java.util.Random;

//the class that controls the whole game and holds all of the game variables.
public class Game {
    //defining variables (static because the Game class is only going to be used once)
    private static int killerTime;
    private static Difficulity difficulity=Difficulity.EASY;
    private static int[][] grid=new int[100][100];
    private static int[] position={5,1};
    private static int[] lastPosition={0,0};
    private static int gridSize=10;
    private static int move=0;
    public static int holder=0;
    private static boolean enableBacktracking=true;
    private static int moveCount=0;
    private static int secPassed=0;
    private static int moveLimit=0;
    private static int livesCount=2;
    private static boolean hasMoved=true;
    private static Direction lastMove;
    
    /**
     * a method to generate randomnumber between x and y
     * @param x the smaller number
     * @param y the bigger number
     * @return returns the random number
     */
    public static int getRandomNum(int x ,int y){
        Random random=new Random();
        return  random.nextInt(y-x+1)+x;
    }
    public static void setRandomTime(){
        killerTime=getRandomNum(10,30);
        // killerTime=2;
    }

    public static int getKillerTime() {
        return killerTime;
    }
    public static Difficulity getDifficulity() {
        return difficulity;
    }
    public static void setDifficulity(Difficulity difficulity) {
        Game.difficulity = difficulity;
    }
    public static void setKillerTime(int killerTime) {
        Game.killerTime = killerTime;
    }
    


    /**
     * starts the game
     */
    public static void startGame(){
        // setting default values for variables
        position[0]=1;
        position[1]=1;
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                grid[i][j]=0;
            }
        }
        grid[0][0]=1;
        // grid[10][10]=2;
        // grid[0][2]=2;
        grid[0][2]=3;

        moveCount=0;
        lastMove=null;

        //generating different gridsize for each difficulity
        switch (difficulity) {
            case EASY:
                gridSize=getRandomNum(30, 60);
                enableBacktracking=false;
                break;
            case MEDIUM:
                gridSize=getRandomNum(50, 80);
                enableBacktracking=true;
                break;
            case HARD:
                enableBacktracking=true;
                gridSize=getRandomNum(70, 100);
            

        }
        moveLimit=((int) (gridSize/10))*3+getRandomNum(5, 20);
        // moveLimit=2;
        // gridSize=16;
        generateObstacles();
        generateLives();
        while(true){
            // killerTime=getRandomNum(10, 30);
            if(hasMoved){
                setRandomTime();
            }
            hasMoved=false;
            
            // killerTime=20;
            if(moveLimit==0){
                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount+"\nmove limit exceeded.");
                return;
            }
            if(((lastMove==Direction.LEFT||lastMove==Direction.RIGHT)&&(grid[position[0]][position[1]]==2)&&(position[0]==1||grid[position[0]-2][position[1]]==2))||((lastMove==Direction.UP||lastMove==Direction.DOWN)&&grid[position[0]-1][position[1]]==2&&(position[1]==1||grid[position[0]-1][position[1]-2]==2))){
                System.out.println("oops you seem to be stuck!");
                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                return;
            }


            holder=gameOver();
            if(holder==1){
                System.out.println("\ncongragulations you have won after "+moveCount+" moves!\nyour total score is:"+calculateScore());
                return;
            }
            else if(holder==-1){
                // System.out.println(killerTime);
                if(outOfLives()){
                    System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                    return;

                }
                if(secPassed>9){
                    backTrack(1);
                }
                else{
                    backTrack(secPassed);
                }
                setRandomTime();
            }
            
            // else if(holder==-1){
            //     // System.out.println(killerTime);
            //     System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);

            //     return;
            // }


            



            System.out.print("1.go left\n2.go right\n3.go down\n4.go up\n5.show current position\n6.give up.\n");
            System.out.println("your position:(row:"+position[0]+",column:"+position[1]+")");
            System.out.println("you have "+moveLimit +" moves left.");
            System.out.println("you have "+killerTime+" seconds left.");
            System.out.println(gridSize);
            System.out.println("number of lives:"+livesCount);
            System.out.println("\033[34;42mgreen light.\033[0m");
            System.out.print("your move:");
            move=ForceNumericInput.force();
            switch (move) {

                case 1:
                    try{
                        holder=goLeft();
                        if(holder==1){
                            System.out.println("\ncongragulations you have won after "+moveCount+" moves!\nyour total score is:"+calculateScore());
                            
                            return;
                        }
                        else if(holder==-1){
                            // System.out.println(killerTime);
                            // System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                            // return;
                            if(outOfLives()){
                                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                                return;
            
                            }
                            prepGrid();
                            if(secPassed>9){
                                backTrack(10);
                            }
                            else{
                                backTrack(secPassed);
                            }
                            setRandomTime();


                        }
                    }catch(Exception e){
                        e.setStackTrace(null);
                    }
                    break;


                case 2:
                    try{
                        holder=goRight();
                        if(holder==1){
                            System.out.println("\ncongragulations you have won after "+moveCount+" moves!\nyour total score is:"+calculateScore());
                            // System.out.println(position[1]);
                            return;
                        }
                        else if(holder==-1){
                            // System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                            // return;


                            if(outOfLives()){
                                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                                return;
            
                            }
                            prepGrid();
                            if(secPassed>9){
                                backTrack(10);
                            }
                            else{
                                // System.out.println(secPassed);

                                backTrack(secPassed);
                            }
                            setRandomTime();

                        }
                    } catch(Exception e){
                        e.setStackTrace(null);
                    }
                    break;
                case 3:
                    try{
                        holder=goDown();
                        if(holder==1){
                            System.out.println("\ncongragulations you have won after "+moveCount+" moves!\nyour total score is:"+calculateScore());
                            return;
                        }
                        else if(holder==-1){
                            // System.out.println(killerTime);
                            // System.out.println(killerTime);
                            if(outOfLives()){
                                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                                return;

                            }
                            prepGrid();
                            if(secPassed>9){
                                backTrack(10);
                            }
                            else{
                                backTrack(secPassed);
                            }
                            setRandomTime();
                        }
                    }catch(Exception e){
                        e.setStackTrace(null);
                    }
                    break;
                
                case 4:
                    try{
                        holder=goUp();
                        if(holder==1){
                            System.out.println("\ncongragulations you have won after "+moveCount+" moves!\nyour total score is:"+calculateScore());
                            
                            return;
                        }
                        else if(holder==-1){
                            // System.out.println(killerTime);
                            if(outOfLives()){
                                System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                                return;

                            }
                            prepGrid();
                            if(secPassed>9){
                                backTrack(10);
                            }
                            else{
                                backTrack(secPassed);
                            }
                            setRandomTime();
                        }
                    }catch(Exception e){
                        e.setStackTrace(null);
                    }
                    break;

                case 5:
                    printGrid("\033[37;42m");
                    break;
                case 6:
                    System.out.println("\nyou have lost after "+moveCount+" moves\nyour score:"+moveCount);
                    return;
                
                
            
            }
            
            

        }
    }

    /**
     * moves 10 times to the right if not blocked
     * @return 1 if the user has won -1 if they've lost 0 if no event has happened.
     * @throws InterruptedException
     */
    public static int goRight() throws InterruptedException{
        if(lastMove==Direction.RIGHT||lastMove== Direction.LEFT){
            System.out.println("you have to move down first!");
            return -2;
        }
        lastMove=Direction.RIGHT;

        secPassed=0;
        moveCount++;
        moveLimit--;
        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        
        printGrid("\033[34m");
        secPassed++;
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }

            if(grid[position[0]-1][position[1]]==2){
                for(int j=1;j<=i-1;j++){
                    holder=gameOver();
                    if(holder==1){
                        return 1;
                    }
                    else if(holder==-1){
                        return-1;
                    }


                    killerTime--;
                    if(killerTime>=0){
                        System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
        
                    }
                    else{
                        System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
                    }
                    secPassed++;
                    grid[lastPosition[0]-1][lastPosition[1]-1]=0;
                    lastPosition[1]++;
                    printGrid();
                    Thread.sleep(220);

                    
                    
                }
                holder=gameOver();
                if(holder==1){
                    return 1;
                }
                else if(holder==-1){
                    return-1;
                }
                if(enableBacktracking&&hasMoved){
                    backTrack(i);
                }
                
                return 0;
            }
            hasMoved=true;
            position[1]++;
            hasHitItem();
            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            
            secPassed++;
            grid[position[0]-1][position[1]-1]=1;
            printGrid();

            System.out.println();

            System.out.flush();
            

            Thread.sleep(220);

            

            
        }
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }
            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            secPassed++;
            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();
            lastPosition[1]++;

            System.out.println();
            // killerTime--;
            Thread.sleep(220);

        }

        holder=gameOver();
        if(holder==1){
            return 1;
        }
        else if(holder==-1){
            return-1;
        }

        System.out.print("\n");
        return 0;
    }


    /**
     * moves 10 times down in a zig-zag shape
     * @return 1 if the user has won -1 if they've lost 0 if no event has happened.
     * @throws InterruptedException
     */
    public static int goDown() throws InterruptedException{

        if(lastMove==Direction.DOWN||lastMove==Direction.UP){
            System.out.println("you have to move left or right first!");
            return -2;
        }
        lastMove=Direction.DOWN;

        secPassed=0;
        moveCount++;
        moveLimit--;

        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        // System.out.print("\033[34m");
        printGrid("\033[34m");
        // System.out.print("\033[0m");
        secPassed++;
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }
            if((i<=4&&grid[position[0]][position[1]]==2)||(i==5&&grid[position[0]][position[1]-1]==2)||(i>5&&i<=9&&grid[position[0]][position[1]-2]==2)){
                for(int j=1;j<i;j++){
                    holder=gameOver();
                    if(holder==1){
                        return 1;
                    }
                    else if(holder==-1){
                        return-1;
                    }
                    killerTime--;
                    if(killerTime>=0){
                        System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
        
                    }
                    else{
                        System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
                    }
                    grid[lastPosition[0]-1][lastPosition[1]-1]=0;
                    secPassed++;
                    printGrid();
                    if(j<=4){
                        lastPosition[1]++;
                    }
                    else if(j>5){
                        lastPosition[1]--;
                    }
                    lastPosition[0]++;
                    Thread.sleep(220);
                    


                }
                holder=gameOver();
                if(holder==1){
                    return 1;
                }
                else if(holder==-1){
                    return-1;
                }
                if(enableBacktracking&&hasMoved){
                    backTrack(i);
                }
                return 0;

            }




            hasMoved=true;

            killerTime--;

            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            // System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
            secPassed++;
            if(i<=4){
                position[1]++;
            }
            else if(i>5){
                position[1]--;
            }
            position[0]++;
            hasHitItem();

            grid[position[0]-1][position[1]-1]=1;
            printGrid();
            
            
            Thread.sleep(220);

            

            
        }
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }


            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }

            secPassed++;

            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();


            if(i<=4){
                lastPosition[1]++;
            }
            else if(i>5){
                lastPosition[1]--;
            }
            lastPosition[0]++;


            

            Thread.sleep(220);

            

            
        }
        holder=gameOver();
        if(holder==1){
            return 1;
        }
        else if(holder==-1){
            return-1;
        }

        System.out.print("\n");
        return 0;
    }


    /**
     * moves 10 times to the left
     * @return 1 if the user has won -1 if they've lost 0 if no event has happened.
     * @throws InterruptedException
     */
    public static int goLeft() throws InterruptedException{
        if(lastMove==Direction.RIGHT||lastMove== Direction.LEFT){
            System.out.println("you have to move down first!");
            return -2;
        }
        // if(position[1]<10){
        //     System.out.println("can't go left it's blocked.");
        //     return -2;
        // }
        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        lastMove=Direction.LEFT;
        secPassed=0;
        moveCount++;
        moveLimit--;


        // System.out.print("\033[34m");
        // printGrid();
        printGrid("\033[34m");
        // System.out.print("\033[0m");
        secPassed++;
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }
            if(position[1]==1||grid[position[0]-1][position[1]-2]==2){
                for(int j=1;j<=i-1;j++){
                    holder=gameOver();
                    if(holder==1){
                        return 1;
                    }
                    else if(holder==-1){
                        return-1;
                    }


                    killerTime--;
                    if(killerTime>=0){
                        System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
        
                    }
                    else{
                        System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
                    }
                    secPassed++;
                    grid[lastPosition[0]-1][lastPosition[1]-1]=0;
                    lastPosition[1]--;
                    printGrid();
                    Thread.sleep(220);

                    
                    
                }
                holder=gameOver();
                if(holder==1){
                    return 1;
                }
                else if(holder==-1){
                    return-1;
                }

                if(enableBacktracking&&hasMoved){
                    backTrack(i);
                }

                return 0;
            }




            hasMoved=true;

            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            secPassed++;

            position[1]--;
            hasHitItem();
            
            grid[position[0]-1][position[1]-1]=1;


            printGrid();

            System.out.println();

            System.out.flush();
            

            Thread.sleep(220);

            

            
        }
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }
            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            secPassed++;

            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            lastPosition[1]--;
            printGrid();

            System.out.println();
            Thread.sleep(220);

        }
        holder=gameOver();
        if(holder==1){
            return 1;
        }
        else if(holder==-1){
            return-1;
        }
        System.out.print("\n");
        return 0;
    }






    /**
     * moves 10 times up in a zig-zag shape
     * @return 1 if the user has won -1 if they've lost 0 if no event has happened.
     * @throws InterruptedException
     */
    public static int goUp() throws InterruptedException{

        if(lastMove==Direction.DOWN||lastMove==Direction.UP){
            System.out.println("you have to move left or right first!");
            return -2;
        }
        lastMove=Direction.UP;

        secPassed=0;
        moveCount++;
        moveLimit--;

        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        // System.out.print("\033[34m");
        // printGrid();
        printGrid("\033[34m");
        // System.out.print("\033[0m");
        secPassed++;
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }
            if((position[0]==1)||(i<=4&&grid[position[0]-2][position[1]]==2)||(i==5&&grid[position[0]-2][position[1]-1]==2)||(i>5&&i<=9&&grid[position[0]-2][position[1]-2]==2)){
                for(int j=1;j<i;j++){
                    holder=gameOver();
                    if(holder==1){
                        return 1;
                    }
                    else if(holder==-1){
                        return-1;
                    }
                    killerTime--;
                    if(killerTime>=0){
                        System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
        
                    }
                    else{
                        System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
                    }
                    grid[lastPosition[0]-1][lastPosition[1]-1]=0;
                    secPassed++;
                    printGrid();
                    if(j<=4){
                        lastPosition[1]++;
                    }
                    else if(j>5){
                        lastPosition[1]--;
                    }
                    lastPosition[0]--;
                    Thread.sleep(220);
                }
                holder=gameOver();
                if(holder==1){
                    return 1;
                }
                else if(holder==-1){
                    return-1;
                }

                if(enableBacktracking&&hasMoved){
                    backTrack(i);

                }

                return 0;

            }

            hasMoved=true;


            killerTime--;

            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }
            // System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");
            secPassed++;
            if(i<=4){
                position[1]++;
            }
            else if(i>5){
                position[1]--;
            }
            position[0]--;
            hasHitItem();

            grid[position[0]-1][position[1]-1]=1;
            printGrid();
            
            
            Thread.sleep(220);

            

            
        }
        for(int i=1;i<=9;i++){
            holder=gameOver();
            if(holder==1){
                return 1;
            }
            else if(holder==-1){
                return-1;
            }


            killerTime--;
            if(killerTime>=0){
                System.out.println("\033[34;42mgreen light:"+secPassed+"\033[0m");

            }
            else{
                System.out.println("\033[34;41mredlight:"+secPassed+"\033[0m");
            }

            secPassed++;

            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();


            if(i<=4){
                lastPosition[1]++;
            }
            else if(i>5){
                lastPosition[1]--;
            }
            lastPosition[0]--;


            

            Thread.sleep(220);

            

            
        }

        holder=gameOver();
        if(holder==1){
            return 1;
        }
        else if(holder==-1){
            return-1;
        }

        System.out.print("\n");
        return 0;
    }



    public static int goRight(int start) throws InterruptedException{
        lastMove=Direction.RIGHT;
        start=11-start;
        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        printGrid();
        for(int i=start;i<=9;i++){
            hasMoved=false;
            position[1]++;
            
            grid[position[0]-1][position[1]-1]=1;
            printGrid();

            System.out.println();

            System.out.flush();
            

            Thread.sleep(220);

            

            
        }
        for(int i=start;i<=9;i++){
            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();
            lastPosition[1]++;

            System.out.println();
            // killerTime--;
            Thread.sleep(220);

        }
        System.out.print("\n");
        return 0;
    }





    public static int goDown(int start) throws InterruptedException{
        start=11-start;

        lastMove=Direction.DOWN;

        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        printGrid();
        for(int i=start;i<=9;i++){

            
            if(i<=4){
                position[1]++;
            }
            else if(i>5){
                position[1]--;
            }
            position[0]++;
            grid[position[0]-1][position[1]-1]=1;
            printGrid();
            Thread.sleep(220);

            

            
        }
        for(int i=start;i<=9;i++){

            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();


            if(i<=4){
                lastPosition[1]++;
            }
            else if(i>5){
                lastPosition[1]--;
            }
            lastPosition[0]++;


            

            Thread.sleep(220);

            

            
        }

        System.out.print("\n");
        return 0;
    }



    public static int goUp(int start) throws InterruptedException{
        start=11-start;

        lastMove=Direction.UP;

        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        printGrid();
        for(int i=start;i<=9;i++){

            
            if(i<=4){
                position[1]++;
            }
            else if(i>5){
                position[1]--;
            }
            position[0]--;
            grid[position[0]-1][position[1]-1]=1;
            printGrid();
            Thread.sleep(220);

            

            
        }
        for(int i=start;i<=9;i++){

            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();


            if(i<=4){
                lastPosition[1]++;
            }
            else if(i>5){
                lastPosition[1]--;
            }
            lastPosition[0]--;


            

            Thread.sleep(220);

            

            
        }

        System.out.print("\n");
        return 0;
    }







    public static int goLeft(int start) throws InterruptedException{
        lastMove=Direction.RIGHT;
        start=11-start;
        lastPosition[0]=position[0];
        lastPosition[1]=position[1];
        printGrid();
        for(int i=start;i<=9;i++){
            hasMoved=false;
            position[1]--;
            
            grid[position[0]-1][position[1]-1]=1;
            printGrid();

            System.out.println();

            System.out.flush();
            

            Thread.sleep(220);

            

            
        }
        for(int i=start;i<=9;i++){
            grid[lastPosition[0]-1][lastPosition[1]-1]=0;
            printGrid();
            lastPosition[1]--;

            System.out.println();
            // killerTime--;
            Thread.sleep(220);

        }
        System.out.print("\n");
        return 0;
    }




    /**
     * checks if the game is over.
     * @return 1 if the user has won -1 if they've lost 0 if no event has happened.
     */
    public static int gameOver(){
        
        if((position[0]>gridSize||position[1]>gridSize)&&killerTime>=0){
            return 1;
        }
        else if(killerTime<0||moveCount<0){
            livesCount--;
            return -1;
        }
        else{
            return 0;
        }
    }

    public static boolean outOfLives(){
        if(livesCount<=0){
            return true;
        }
        return false;
    }

    /**
     * calculates the score of the user base on the difficulity
     * @return returns the score
     */
    public static int calculateScore(){
        switch (difficulity) {
            case EASY:
                return moveCount+10;
            case MEDIUM:
                return moveCount +20;
            case HARD:
                return moveCount+40;
        
        }
        return 0;
    }


    /**
     * generates obstacles on the playground based on the difficulity.
     */
    public static void generateObstacles(){
        int temp=0;
        switch(difficulity){
            case EASY:
                break;
            case MEDIUM:
                grid[0][getRandomNum(1, gridSize-1)]=2;
                for(int i=1;i<gridSize;i++){
                    temp=getRandomNum(1, 2);
                    if(temp==1){
                        grid[i][getRandomNum(0, gridSize-1)]=2;
                    }
                }
                break;
            case HARD:
                grid[0][getRandomNum(1, gridSize-1)]=2;
                grid[0][getRandomNum(1, gridSize-1)]=2;
                for(int i=1;i<gridSize;i++){
                    grid[i][getRandomNum(0, gridSize-1)]=2;
                    temp=getRandomNum(1, 2);
                    if(temp==1){
                        grid[i][getRandomNum(0, gridSize-1)]=2;
                    }

                }

        }

    }

    public static void generateLives(){
        int temp=0;
        switch(difficulity){
            case EASY:
                for(int i=1;i<gridSize;i++){
                    temp=getRandomNum(1, 5);
                    if(temp==1){
                        grid[i][getRandomNum(0, gridSize-1)]=3;
                    }
                }
                break;
            case MEDIUM:
                for(int i=1;i<gridSize;i++){
                    temp=getRandomNum(1, 10);
                    if(temp==1){
                        grid[i][getRandomNum(0, gridSize-1)]=3;
                    }
                }
                break;
            case HARD:
                for(int i=1;i<gridSize;i++){
                    temp=getRandomNum(1, 25);
                    if(temp==1){
                        grid[i][getRandomNum(0, gridSize-1)]=3;
                    }
                }
                
        }

    }



    public static void hasHitItem(){
        if(grid[position[0]-1][position[1]-1]==3){
            livesCount++;
            System.out.println("congrats!you gained an extra life!");
            
            return;
        }
    }
    
    public static void printGrid(){
        System.out.print(" ");
        for(int i=0;i<=gridSize;i++){
            System.out.print("_");
        }
        System.out.println();
        for(int i=0;i<=gridSize;i++){
            System.out.print("|");
            for(int j=0;j<=gridSize;j++){
                if(grid[i][j]==0){
                    System.out.print(" ");
                }
                else if(grid[i][j]==1){
                    System.out.print("*");
                }
                else if(grid[i][j]==2){
                    System.out.print("\033[33;41m#\033[0m");
                }
                else if(grid[i][j]==3){
                    System.out.print("\033[33;44m.\033[0m");
                }
            }
            System.out.print("|\n");
            // System.out.println();
            
        }
        System.out.print(" ");
        for(int i=0;i<=gridSize;i++){
            System.out.print("-");
        }
        System.out.println();
    }

    public static void printGrid(String starColor){
        System.out.print(" ");
        for(int i=0;i<=gridSize;i++){
            
            System.out.print("_");
        }
        System.out.println();
        for(int i=0;i<=gridSize;i++){
            System.out.print("|");
            for(int j=0;j<=gridSize;j++){
                if(grid[i][j]==0){
                    System.out.print(" ");
                }
                else if(grid[i][j]==1){
                    System.out.print(starColor);
                    System.out.print("*");
                    System.out.print("\033[0m");
                }
                else if(grid[i][j]==2){
                    System.out.print("\033[33;41m#\033[0m");
                }
                else if(grid[i][j]==3){
                    System.out.print("\033[33;44m.\033[0m");
                }
            }
            System.out.print("|\n");
            
        }
        System.out.print(" ");
        for(int i=0;i<=gridSize;i++){
            System.out.print("-");
        }
        System.out.println();
    }

    public static void prepGrid(){
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(grid[i][j]==1){
                    grid[i][j]=0;

                }
            }
        }
        grid[position[0]-1][position[1]-1]=1;

    }


    public static void backTrack(int sec){
        if(lastMove==null){
            return;
        }
        hasMoved=false;
        killerTime+=sec*2;
        killerTime-=getRandomNum(4, 8);
        System.out.println("oops you hit an obstacle you got some time penalty!\nyour movement limitations are gonr you can move in any direction you want!");
        System.out.println("backtracking you to your last position...");
        switch(lastMove){
            case RIGHT:
                try{
                    goLeft(sec);
                }catch(Exception e){

                }   
                lastMove=Direction.DOWN;
                break;
            case LEFT:
                try{
                    goRight(sec);
                }catch(Exception e){

                }   
                lastMove=Direction.DOWN;
                break;
            case UP:
                try{
                    goDown(sec);
                }catch(Exception e){

                }   
                lastMove=Direction.RIGHT;
                break;
            case DOWN:
                try{
                    goUp(sec);
                }catch(Exception e){

                }   
                lastMove=Direction.RIGHT;

                break;


                
                
            
        }
        lastMove=null;

    }

}


