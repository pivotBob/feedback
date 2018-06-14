package com.rwlarsen.feedback;

public class Main {
    public static void main(String[] args) {
        PairGenerator pairGenerator = new PairGeneratorImpl();
        TeamDataStore dataStore = new FileTeamDataStore();
        Controller controller = new Controller(pairGenerator, dataStore);
        controller.run();
    }
}
