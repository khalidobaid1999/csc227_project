import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Util {
    static Random random = new Random();

    public static void main(String[] args) {
        File f = new File("input.txt");
        try {
            writeFile(10);

            Queue<Process> readFile = readFile(f);
            Memory mainMemory = new Memory(readFile, 1024);
            CPU mainProcessor = new CPU(mainMemory);

            mainMemory.longTermScheduele();
            while (!mainMemory.areQueuesEmpty() || mainProcessor.runningProcess != null)
            {
            mainProcessor.simulateMachineExecuteCycle();
            }
            System.out.println(mainMemory.finishedProcesses);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Queue<Process> readFile(File f) throws FileNotFoundException {
        Queue<Process> q = new LinkedList<Process>();
        Scanner sc = new Scanner(f);

        while (sc.hasNextLine()) {

            String currentProcessName = "";
            ArrayList<Integer> currentIObursts = new ArrayList<Integer>();
            ArrayList<Integer> currentCPUbursts = new ArrayList<Integer>();
            ArrayList<Integer> currentMemoryUsage = new ArrayList<Integer>();

            String currentLine = sc.nextLine();
            if (currentLine.matches("")) {
                continue;
            }
            String[] split = currentLine.split("\\[");
            currentProcessName = split[0];
            getList(currentCPUbursts, split[1]);
            getList(currentMemoryUsage, split[2]);
            getList(currentIObursts, split[3]);
            currentCPUbursts.remove(Integer.valueOf(-1));

            Process p = new Process(currentProcessName, currentCPUbursts, currentIObursts, currentMemoryUsage);
            q.add(p);

            currentIObursts = new ArrayList<Integer>();
            currentCPUbursts = new ArrayList<Integer>();
            currentMemoryUsage = new ArrayList<Integer>();

        }

        sc.close();
        return q;
    }

    private static void getList(ArrayList<Integer> currentCPUbursts, String substring) {
        substring = substring.trim().replaceAll("[\\[\\]]", "");
        String[] split = substring.split(",");
        for (String string : split) {
            currentCPUbursts.add(Integer.parseInt(string.trim()));
        }
    }

    // TODO: Implement the process statistics output
    public static void writeProcessOutputs(LinkedList<Process> finishedProcesses) {
        return;
    }

    public static void writeFile(int n) throws IOException {
        File f = new File("input.txt");
        PrintWriter w = new PrintWriter(f);

        for (int i = 0; i < n; i++) {
            w.println(getRandomProcess(i));
        }

        w.close();
    }

    private static Process getRandomProcess(int i) {
        Process process = new Process();
        process.setId(i);
        process.setName(generateProcessName()); // Change to string later.
        process.setArrivalTime(getNumber(1, 80));
        int n = getNumber(4, 9);

        List<Integer> cpu = new ArrayList<>(), memory = new ArrayList<>(), io = new ArrayList<>();
        process.setCPUBursts(cpu);
        process.setMemoryUsage(memory);
        process.setIOBursts(io);
        int mem = 0, c;

        for (int j = 0; j < n; j++) {
            cpu.add(getNumber(10, 100));
            while (mem + (c = getNumber(5, 200) * (getNumber(1, 100) > 50 ? 1 : -1)) < 0)
                ;
            memory.add(c);
            mem += c;
            io.add(getNumber(20, 60));
        }

        io.set(io.size() - 1, -1);
        return process;
    }

    private static int getNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private static String generateProcessName() {
        String output = "";

        for (int i = 0; i < 8; i++) {
            output += (char) getNumber('A', 'Z');
        }

        return output;
    }

}
