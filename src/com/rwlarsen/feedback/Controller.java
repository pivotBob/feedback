package com.rwlarsen.feedback;

import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private final PairGenerator pairGenerator;
    private final TeamDataStore dataStore;

    public Controller(PairGenerator pairGenerator, TeamDataStore dataStore) {
        this.pairGenerator = pairGenerator;
        this.dataStore = dataStore;
    }

    public void run() {
        Team team = dataStore.load();
        Set<TeamMember> activeMembers = team.getMembers().stream()
                .filter(tm -> tm.isActive() && tm.isPresent())
                .collect(Collectors.toSet());
        Set<Pair> pairs = pairGenerator.generatePairs(activeMembers);
        pairs.forEach(pair -> System.out.println(pair.getMember1().getName() + " <=> " + pair.getMember2().getName()));
        team.getHistory().addAll(pairs);
        dataStore.save(team);
    }
}
