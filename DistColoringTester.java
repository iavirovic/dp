public class DistColoringTester {

    public static void main(String[] args) throws Exception {
        String baseName = args[0];
        int myId = Integer.parseInt(args[1]);
        int numProc = Integer.parseInt(args[2]);
        Linker comm = new Linker(baseName, myId, numProc);
        
        DistributedColoring dc = new DistributedColoring(comm, myId);

        for (int i = 0; i < numProc; i++)
            if (i != myId) (new ListenerThread(i, dc)).start();
        dc.initiate(); // start the computation
    }    
}
