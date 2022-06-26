public class MaxIndSetTester  {

    public static void main(String[] args) throws Exception {
        String baseName = args[0];
        int myId = Integer.parseInt(args[1]);
        int myColor = Integer.parseInt(args[2]);
        int numProc = Integer.parseInt(args[3]);
        int colorsNum = Integer.parseInt(args[4]);
        Linker comm = new Linker(baseName, myId, numProc);
        Synchronizer pulser = new SimpleSynch(comm);

        MaxIndependentSet dc = new MaxIndependentSet(comm, myId, pulser, myColor, colorsNum);

        for (int i = 0; i < numProc; i++)
            if (i != myId) (new ListenerThread(i, pulser)).start();
        dc.initiate(); // start the computation
    }    
}
