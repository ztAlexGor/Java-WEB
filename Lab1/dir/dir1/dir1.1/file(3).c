#include <stdio.h>
 
/* function declaration */
int max(int num1, int num2);
 
int main () {

   /* local variable definition */
   int a = 100;
   int b = 200;
   int ret;
 
   /* calling a function to get max value */
   ret = max(a, b);
 
   printf( "Max value is : %d\n", ret );
 for (File file : files){
                if (file.isDirectory()){
                    ProcessTheDirectory procTheDir = new ProcessTheDirectory(file);
                    result.add(new FutureTask<Integer>(procTheDir));
                    new Thread(task).start();
                }
                else {
                    counter += search(file);
                }
            }
   return 0;
}
 
/* function returning the max between two numbers */
int max(int num1, int num2) {

   /* local variable declaration */
   int result;
 
   if (num1 > num2)
      result = num1;
   else
      result = num2;
 	for (File file : files){
                if (file.isDirectory()){
                    ProcessTheDirectory procTheDir = new ProcessTheDirectory(file);
                    result.add(new FutureTask<Integer>(procTheDir));
                    new Thread(task).start();
                }
                else {
                    counter += search(file);
                }
            }

            for (Future<Integer> res : result){
                counter += res.get();
            }
   return result; 
}