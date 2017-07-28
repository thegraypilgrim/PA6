import java.util.*;
import java.io.*;
public class PA6{
        private static int currentCost = 2147483647;

        /*
         *input: A 2d Int array that is NxN where N is the second argument. Also a File object that the data set up for this file.
         *
         *Out: Sets the 2d array to what the data in the file said. any [n][n] == 0;
         */
        public static void populateMatrix(int[][]  adjacency, int CITI, File f){ //CITI is the number of cities and it is a constant  

                try{
                        Scanner input = new Scanner(f);
                        int value, i, j;
                        for (i = 0; i < CITI && input.hasNext(); i++) {
                                for (j = i; j < CITI && input.hasNext(); j++){
                                        if (i == j) {
                                                adjacency[i][j] = 0;

                                        }
                                        else {
                                                value = input.nextInt();
                                                adjacency[i][j] = value;
                                                adjacency[j][i] = value;


                                        }

                                }

                        }
                        input.close();


                }
                catch(IOException e){
                        System.out.println("file fail" + e);


                }


        }

        /*
         *input: A 2d int array that has been put into populateMatrix and the int that was run with it. 
         *
         *Out: prints out the starting city(always 0), a path that visits all city and the cost of that path. returns nothing 
         */

        public static void find(int[][] matrix, int CITI){

                Stack<Integer> pathStack = new Stack<Integer>();
                ArrayList<Integer> visitedCities = new ArrayList<Integer>();

                visitedCities.add(0);
                pathStack.push(0);

                int closestCity = 0;
                boolean minFlag = false;
                System.out.println("the starting city is 0");

                while(!pathStack.empty()){
                        int currentCity = pathStack.peek();
                        int min = Integer.MAX_VALUE;
                        for(int i = 1; i < CITI; i++){
                                if(matrix[currentCity][i] != 0 && !visitedCities.contains(i)){
                                        if(matrix[currentCity][i] < min){
                                                min = matrix[currentCity][i];
                                                closestCity = i;
                                                minFlag = true;
                                        } 
                                }
                        }
                        if(minFlag){
                                visitedCities.add(closestCity);
                                pathStack.push(closestCity);
                                System.out.print(closestCity + " ");
                                minFlag = false;
                                continue;
                        }
                        pathStack.pop();

                };

                System.out.println();
                System.out.println("the cost is " + cost(visitedCities, matrix));
        }

        /*
         *input: An ArrayList of positive Integers and a 2d ind array that leangth should be greater than all number in the ArrayList
         *
         *Out: Return the sum of the indexes of all adjacent numberis in the ArrayList
         */

        private static int cost(ArrayList<Integer> v, int[][] matrix){
                int pathcost = 0;
                for(int i = 1; i < v.size(); i++){
                        pathcost += matrix[v.get(i-1)][v.get(i)];
                }
                pathcost += matrix[v.get(v.size() -1)][v.get(0)];
                return pathcost;       
        }
        public static void main(String[] args){
                int[] files = {12, 13, 14, 15, 16, 19, 29};
                for(int i = 0; i < files.length; i++){
                        File f = new File("tsp" + files[i] + ".txt");
                        int CITI = files[i];
                        int[][] matrix = new int[CITI][CITI];

                        System.out.println("number of cities " + files[i]);
                        populateMatrix(matrix, CITI, f);
                        long start = System.nanoTime();
                        find(matrix, CITI);
                        System.out.println("the time for " + files[i] + " cities is " + (System.nanoTime() - start));       
                }
        }
}
