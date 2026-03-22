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
        //TODO: EXAMPLE RUn X

        //testing 1.a)
        Bankers bankers1 = new Bankers(4, 3);
        bankers1.available = new int[]{3, 1, 2};
        bankers1.maximum = new int[][]{{3, 2, 2}, {2, 2, 2}, {4, 2, 2}, {2, 1, 3}};
        bankers1.allocation = new int[][]{{1, 0, 1}, {2, 1, 0}, {1, 1, 1}, {0, 0, 2}};
        bankers1.need = new int[][]{{2, 2, 1}, {0, 1, 2}, {3, 1, 1}, {2, 1, 1}};
        System.out.println("Is 1a safe: " + bankers1.isSafe());

    }
}