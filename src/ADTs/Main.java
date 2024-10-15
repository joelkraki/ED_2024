package ADTs;

import Exceptions.EmptyCollectionException;

public class Main {
    public static void main(String[] args){
        WeirdQueueManager weirdQueueManager = new WeirdQueueManager();

        weirdQueueManager.addString("A");
        weirdQueueManager.addString("B");
        weirdQueueManager.addString("C");
        weirdQueueManager.addString("D");

        try {
            weirdQueueManager.mergeQueues();
        } catch (EmptyCollectionException e) {
            System.out.println(e);
            return;
        }

        System.out.println(weirdQueueManager);


    }
}
