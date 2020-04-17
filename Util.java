import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Util {
    static Random random = new Random();

    public static void main(String[] args) throws IOException {
//        writeFile(1000);
        Queue<Process> jobs = readFile("input.txt");

        for (Process process : jobs) {
            System.out.println(process);
        }
    }

    public static Queue<Process> readFile(String file) throws IOException {
        Queue<Process> q = new PriorityBlockingQueue<>(1000, Comparator.comparingInt(Process::getArrivalTime));
        Scanner sc = new Scanner(new File(file));

        while (sc.hasNext()) {
            String values[] = sc.nextLine().split("\\|");
            int line[] = Arrays.asList(values).stream().mapToInt(Integer::parseInt).toArray();
            Process process = new Process();

            process.setId(line[0]);
            process.setName("" + line[1]); // Change to string later?
            process.setArrivalTime(line[2]);

            List<Integer> cpu = new ArrayList<>(), memory = new ArrayList<>(), io = new ArrayList<>();
            process.setCPUBursts(cpu);
            process.setMemoryUsage(memory);
            process.setIOBursts(io);

            for (int i = 3; i < line.length; i+=3) {
                cpu.add(line[i]);
                memory.add(line[i+1]);
                io.add(line[i+2]);
            }
            q.add(process);
        }

        sc.close();
        return q;
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
        process.setName("" + random.nextInt()); // Change to string later.
        process.setArrivalTime(getNumber(1, 80));
        int n = getNumber(4, 9);

        List<Integer> cpu = new ArrayList<>(), memory = new ArrayList<>(), io = new ArrayList<>();
        process.setCPUBursts(cpu);
        process.setMemoryUsage(memory);
        process.setIOBursts(io);
        int mem = 0, c;

        for (int j = 0; j < n; j++) {
            cpu.add(getNumber(10, 100));
            while (mem + (c = getNumber(5, 200) * (getNumber(1, 100) > 50 ? 1 : -1)) < 0);
            memory.add(c);
            mem += c;
            io.add(getNumber(20, 60));
        }

        io.set(io.size()-1, -1);
        return process;
    }

    private static int getNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
