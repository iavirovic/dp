import java.io.*;
import java.util.*;
public class Topology {
    public static int readNeighbors(int myId, int N,
                                     IntLinkedList neighbors) {
        Util.println("Reading topology");
        int maxDegree = 0;
        try {
                for (int i = 0 ; i < N ; ++i) {
                    if (i != myId) 
                        readOne(i, neighbors);
                    maxDegree = Math.max(maxDegree, neighbors.size());
                    neighbors.clear();
                }
                readOne(myId, neighbors);
                maxDegree = Math.max(maxDegree, neighbors.size());
        }
        catch (FileNotFoundException e) {
            for (int j = 0; j < N; j++)
                if (j != myId) neighbors.add(j);
            maxDegree = neighbors.size();
        } catch (IOException e) {
            System.err.println(e);
        }
        Util.println(neighbors.toString());

        return maxDegree;
    }

    public static void readOne(int id, IntLinkedList neighbors) throws FileNotFoundException, IOException {
        BufferedReader dIn = new BufferedReader(
				new FileReader("topology1/topology" + id));
            StringTokenizer st = new StringTokenizer(dIn.readLine());
        while (st.hasMoreTokens()) {
            int neighbor = Integer.parseInt(st.nextToken());
            neighbors.add(neighbor);
        }
    }
}