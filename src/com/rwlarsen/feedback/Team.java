package com.rwlarsen.feedback;

import java.util.HashSet;
import java.util.Set;

public class Team {
    private Set<TeamMember> members;
    private Set<Pair> history;

    public Set<TeamMember> getMembers() {
        if (members == null)
            members = new HashSet<>();
        return members;
    }

    public void setMembers(Set<TeamMember> members) {
        this.members = members;
    }

    public Set<Pair> getHistory() {
        if (history == null)
            history = new HashSet<>();
        return history;
    }

    public void setHistory(Set<Pair> history) {
        this.history = history;
    }
}
