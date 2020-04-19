import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        
        String currentProcessName = "";
        ArrayList<Integer> currentIObursts = new ArrayList<Integer>();
        ArrayList<Integer> currentCPUbursts = new ArrayList<Integer>();
        ArrayList<Integer> currentMemoryUsage = new ArrayList<Integer>();        

        while(sc.hasNextLine()){
            String currentLine = sc.nextLine();
            if(currentLine.matches("")){
                continue;
            }

            if(currentLine.matches("^\\d*$")){
                currentIObursts.add(Integer.parseInt(currentLine));
            } else if(currentLine.matches("[0-9]*\\s[-]?[0-9]*")){
                String[] input = currentLine.split(" ");
                currentCPUbursts.add(Integer.parseInt(input[0]));
                currentMemoryUsage.add(Integer.parseInt(input[1]));

            } else if(currentLine.matches(".*[a-z].*")){
                currentProcessName =  currentLine;
            } else if(currentLine.matches("-1")){
                Process p = new Process(currentProcessName,currentCPUbursts, currentIObursts, currentMemoryUsage);
                q.add(p);
                    
                currentIObursts = new ArrayList<Integer>();
                currentCPUbursts = new ArrayList<Integer>();
                currentMemoryUsage = new ArrayList<Integer>(); 
            }
        }

        sc.close();
        return q;
    }

}
