public class detection {
	private final int[] available;
	private final int[][] allocation;
	private final int[][] request;
	private final int numProcesses;
	private final int numResources;

	public detection(int numProcesses, int numResources) {
		this.numProcesses = numProcesses;
		this.numResources = numResources;
		this.available = new int[numResources];
		this.allocation = new int[numProcesses][numResources];
		this.request = new int[numProcesses][numResources];
	}

	public void setAvailable(int[] values) {
		for (int i = 0; i < numResources; i++) {
			available[i] = values[i];
		}
	}

	public void setAllocation(int process, int[] values) {
		for (int i = 0; i < numResources; i++) {
			allocation[process][i] = values[i];
		}
	}

	public void setRequest(int process, int[] values) {
		for (int i = 0; i < numResources; i++) {
			request[process][i] = values[i];
		}
	}

	public int[] detection() {
		boolean[] finish = new boolean[numProcesses];
		int[] work = available.clone();

		for (int i = 0; i < numProcesses; i++) {
			boolean zeroAllocation = true;
			for (int j = 0; j < numResources; j++) {
				if (allocation[i][j] != 0) {
					zeroAllocation = false;
					break;
				}
			}
			finish[i] = zeroAllocation;
		}

		boolean progress;
		do {
			progress = false;
			for (int i = 0; i < numProcesses; i++) {
				if (finish[i]) {
					continue;
				}

				boolean canRun = true;
				for (int j = 0; j < numResources; j++) {
					if (request[i][j] > work[j]) {
						canRun = false;
						break;
					}
				}

				if (canRun) {
					for (int j = 0; j < numResources; j++) {
						work[j] += allocation[i][j];
					}
					finish[i] = true;
					progress = true;
				}
			}
		} while (progress);

		int deadlockedCount = 0;
		for (boolean state : finish) {
			if (!state) {
				deadlockedCount++;
			}
		}

		int[] deadlocked = new int[deadlockedCount];
		int index = 0;
		for (int i = 0; i < numProcesses; i++) {
			if (!finish[i]) {
				deadlocked[index++] = i;
			}
		}
		return deadlocked;
	}

	public static void main(String[] args) {

        //TODO RUN TEST
		detection detector = new detection(4, 3);

		detector.setAvailable(new int[] {1, 0, 0});

		detector.setAllocation(0, new int[] {1, 0, 0});
		detector.setAllocation(1, new int[] {0, 1, 0});
		detector.setAllocation(2, new int[] {1, 1, 0});
		detector.setAllocation(3, new int[] {0, 0, 1});
	

		detector.setRequest(0, new int[] {0, 0, 0});
		detector.setRequest(1, new int[] {0, 0, 2});
		detector.setRequest(2, new int[] {1, 0, 0});
		detector.setRequest(3, new int[] {0, 2, 0});
		

		int[] deadlocked = detector.detection();

		if (deadlocked.length == 0) {
			System.out.println("No deadlock detected.");
		} else {
			System.out.print("These processes are deadlocked: ");
			for (int i = 0; i < deadlocked.length; i++) {
				System.out.print("P" + deadlocked[i]);
				if (i < deadlocked.length - 1) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}
}
