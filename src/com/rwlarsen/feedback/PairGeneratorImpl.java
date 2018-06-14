package com.rwlarsen.feedback;

import java.time.LocalDate;
import java.util.*;

public class PairGeneratorImpl implements PairGenerator {

    @Override
    public Set<Pair> generatePairs(Set<TeamMember> allMembers) {
        TeamMember proposer = allMembers.stream()
                .filter(tm -> tm.getPossibilities().get(0) != tm.getCurrentPair())
                .findAny().orElse(null);
        while (proposer != null) {
            TeamMember decider = proposer.getPossibilities().get(0);
            if (acceptsProposal(proposer,decider)) {
                accept(proposer, decider);
                accept(decider, proposer);
            } else {
                proposer.getPossibilities().remove(decider);
            }
            proposer = allMembers.stream()
                    .filter(tm -> tm.getPossibilities().get(0) != tm.getCurrentPair())
                    .findAny().orElse(null);
        }

        Set<Pair> pairs = new TreeSet<>();
        allMembers.forEach(tm -> {
            if (pairs.stream().noneMatch(p -> p.contains(tm))) {
                pairs.add(new Pair(tm, tm.getPossibilities().get(0), LocalDate.now()));
            }
        });
        return pairs;
    }

    private void accept(TeamMember a, TeamMember b) {
        if (a.getCurrentPair()!=null){
            a.getCurrentPair().setCurrentPair(null);
            a.getCurrentPair().getPossibilities().remove(a);
        }
        a.setCurrentPair(b);
    }

    private boolean acceptsProposal(TeamMember proposer, TeamMember decider) {
        if (!decider.getPossibilities().contains(proposer)) {
            return false;
        }
        if (decider.getCurrentPair() == null) {
            return true;
        }
        return (decider.getPossibilities().indexOf(decider.getCurrentPair()) > decider.getPossibilities().indexOf(proposer));
    }

}
