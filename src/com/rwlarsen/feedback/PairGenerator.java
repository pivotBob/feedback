package com.rwlarsen.feedback;

import java.util.Set;

public interface PairGenerator {
    Set<Pair> generatePairs(Set<TeamMember> allMembers);
}
