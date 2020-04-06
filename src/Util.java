import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Util {
    public static void main(String[] args) {
        File f = new File("sampleFile.txt");
        try {
            Queue<Process> readFile = readFile(f);
            while(!readFile.isEmpty())
                System.out.println(readFile.poll());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Queue<Process> readFile(File f) throws FileNotFoundException {
        Queue<Process> q = new LinkedList<Process>();
        Scanner sc = new Scanner(f);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            Process p = new Process(sc);
            q.add(p);
        }
        sc.close();
        return q;
    }

}
