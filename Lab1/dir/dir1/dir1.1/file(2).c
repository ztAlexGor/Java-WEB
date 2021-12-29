#include <stdio.h>
 
/* function declaration */
int max(int num1, int num2);
 
int main () {

   ret = max(a, b);
 
   printf( "Max value is : %d\n", ret );
 
   return 0;
}
 
/* function returning the max between two numbers */
int max(int num1, int num2) {

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