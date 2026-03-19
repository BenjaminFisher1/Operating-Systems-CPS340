public class Bankers {
    private int[] available;
    private int[][] maximum;
    private int[][] allocation;
    private int[][] need;
    private int numProcesses;
    private int numResources;

    public Bankers(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        this.available = new int[numResources];
        this.maximum = new int[numProcesses][numResources];
        this.allocation = new int[numProcesses][numResources];
        this.need = new int[numProcesses][numResources];
    }

    public boolean isSafe() {
        boolean[] finished = new boolean[numProcesses];
        int[] temp = available.clone();
        int count = 0;

        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finished[i] && canAllocate(i, temp)) {
                    finished[i] = true;
                    for (int j = 0; j < numResources; j++) {
                        temp[j] += allocation[i][j];
                    }
                    found = true;
                    count++;
                    break;
                }
            }
            if (!found) break;
        }
        return count == numProcesses;
    }

    private boolean canAllocate(int process, int[] temp) {
        for (int i = 0; i < numResources; i++) {
            if (need[process][i] > temp[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //TODO: EXAMPLE RUn
    }
}