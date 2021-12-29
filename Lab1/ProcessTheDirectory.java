import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;


public class ProcessTheDirectory implements Callable <Integer>{
    private File directory;
    private ExecutorService pool;


    public ProcessTheDirectory(File directory, ExecutorService pool) throws Exception{
        this.directory = directory;
        this.pool = pool;
        if (directory.listFiles() == null) throw new Exception("No directory");
    }


    public Integer call(){
        int counter = 0;

        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> result = new ArrayList<>();
            
            for (File file : files){
                if (file.isDirectory()){
                    // ProcessTheDirectory procTheDir = new ProcessTheDirectory(file, pool);
                    result.add(pool.submit(new ProcessTheDirectory(file, pool)));
                }
                else if (file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("c")) {
                    result.add(pool.submit(new SearchInFile(file, "for")));
                }
            }

            for (Future<Integer> res : result){
                counter += res.get();
            }
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return counter;
    }
}
