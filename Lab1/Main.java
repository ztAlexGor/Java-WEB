import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.io.File;


public class Main{
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        ExecutorService pool = Executors.newCachedThreadPool();
        boolean flag = true;

        while (flag){
            System.out.print("Enter your directory: ");
            String dir = sc.nextLine();
            

            try {
                Future<Integer> res = pool.submit(new ProcessTheDirectory(new File(dir), pool));
                flag = false;

                try {
                    System.out.println("\nTotal amount of 'for' operator is: " + res.get());
                    pool.shutdown();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                System.out.println("Incorrect directory. Please try again.");
            }
        }

        sc.close();
    }
}