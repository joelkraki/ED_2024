package ADTs;

public class SolveTowers {
    /**
     * Creates a TowersOfHanoi puzzle and solves it.
     */
    public static void main (String[] args)
    {
        TowersOfHanoi towers = new TowersOfHanoi (4);
        towers.solve();
    }
}
