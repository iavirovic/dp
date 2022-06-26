import java.util.Arrays;

public class MaxIndependentSet extends Process {
    int id;
    int color;
    int m;
    Synchronizer s;
    int[] selected = new int[N];

    public MaxIndependentSet(Linker initComm, int myId, Synchronizer initS, int myColor, int colorsNum) {
        super(initComm);
        s = initS;
        id = myId;
        m = colorsNum;
        color = myColor;
        Arrays.fill(selected,0);

    }

    public void initiate() {
        s.initialize(this);
        for (int pulse = 0; pulse < m; pulse++) {
            if (color == pulse) {
                boolean flag = true;
                for (int i = 0; i < N; i++) {
                    if (isNeighbor(i) && selected[i] == 1) flag = false;
                }
                if (flag) {
                    selected[id] = 1;
                }
            }
            for (int i = 0; i < N; i++) {
                if (isNeighbor(i)) {
                    //Util.println("saljem " + Integer.toString(i) + " " + Integer.toString(id));
                    s.sendMessage(i, "selected", selected[id]);
                }
            }
            
            s.nextPulse();
        }
        if (selected[id] == 1) Util.println("JA SAM DIO MAKSIMALNOG NEZAVISNOG SKUPA");
        else Util.println("JA NISAM DIO MAKSIMALNOG NEZAVISNOG SKUPA");
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("selected")) {
           selected[src] = m.getMessageInt();
        }
    }
}
