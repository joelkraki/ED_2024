package ADTs;

public class TowersOfHanoi {

    private int numDisks;

    public TowersOfHanoi(int numTowers) {
        this.numDisks = numTowers;
    }


    public void solve() {
        moveTowers(this.numDisks, 1, 3, 2);
    }

    private void moveTowers(int numDisks, int start, int end, int extra) {
        if (numDisks == 1) {
            moveOneTower(start, end);
            return;
        }

        moveTowers(numDisks - 1, start, extra, end);
        moveOneTower(start, end);
        moveTowers(numDisks - 1, extra, end, start);

    }

    private void moveOneTower(int start, int end) {
        System.out.println("Moved one disk from tower " + start + " to tower " + end);
    }
}
