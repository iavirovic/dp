import java.util.Arrays;
import java.util.StringTokenizer;

public class DistributedColoring extends Process {
    int[] color = new int[N];
    int r = -1, id;
    int delta;
    int received = 0, numNeighs = 0;

    public DistributedColoring(Linker initComm, int myId) {
        super(initComm);
        Arrays.fill(color,-1);
        id = myId;
        delta = initComm.maxDegree;
        r = delta;
        color[id] = id;
    }

    public void initiate() {
        sendToNeighbors("init", color[id]);
    }
    
    @Override
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("init")) {
            int col = m.getMessageInt();
            color[src] = col;
            ++numNeighs;
            if (canStart()) {
                ++r;
                notifyAll();
                nextRound();
            }
        }
        else if (tag.equals("rnd_col")) {
            StringTokenizer st = new StringTokenizer(m.getMessage());
            int rnd = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());

            while (rnd != r)
                myWait();

            color[src] = col;
            received++;
            if (received == numNeighs) {
                received = 0;
                ++r;
                notifyAll();
                nextRound();
            }
        }
    }

    public void nextRound() {
        if (r == N) {
            Util.println("Process " + Integer.toString(id) + " ended with color " + Integer.toString(color[id]));
            return;
        }
        if (color[id] == r) {
            for (int i = 0; i <= delta; ++i) {
                boolean found = false;
                for (int j = 0 ; j < N ; ++j) {
                    if (i == color[j]) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    color[id] = i;
                    break;
                }
            }
        }
        sendToNeighbors("rnd_col", r, color[id]);
    }

    private boolean canStart() {
        boolean canStart = true;

        for(int i = 0; i < N ; ++i )
            if (i != id && isNeighbor(i)) 
                canStart = canStart && color[i] != -1;

        return canStart;
    }
}