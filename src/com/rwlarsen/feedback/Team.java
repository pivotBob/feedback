package com.rwlarsen.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Team {
    private ObservableList<TeamMember> members = FXCollections.observableArrayList();
    private Set<Pair> history;

    public ObservableList<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(Set<TeamMember> members) {
        this.members.clear();
        this.members.setAll(members);
    }

    public Set<Pair> getHistory() {
        if (history == null)
            history = new HashSet<>();
        return history;
    }

    public void setHistory(Set<Pair> history) {
        this.history = history;
    }

    @JsonIgnore
    public Set<TeamMember> getActiveMembers() {
        Set<TeamMember> activeMembers = getMembers().stream()
            .filter(tm -> tm.isActive() && tm.isPresent())
            .collect(Collectors.toSet());
        if (activeMembers.size() % 2 == 1) {
            activeMembers.add(new TeamMember("Corner", true, true, false));
        }
        activeMembers.forEach(teamMember -> {
            if (teamMember.isClient()) {
                setPreferencesForClient(teamMember, activeMembers);
            } else if ("Corner".equals(teamMember.getName())) {
                setPreferencesForCorner(teamMember, activeMembers);
            } else {
                setPreferencesForPivot(teamMember, activeMembers);
            }
            teamMember.resetPossibilities();
        });
        return activeMembers;
    }

    private LocalDate mostRecentPair(TeamMember a, TeamMember b) {
        return getHistory().stream()
            .filter(pair -> pair.contains(a) && pair.contains(b))
            .sorted(Comparator.comparing(Pair::getDate).reversed())
            .map(pair -> pair.getDate())
            .findFirst().orElse(LocalDate.of(2000, 01, 01));
    }

    private void setPreferencesForClient(TeamMember client, Set<TeamMember> teamMembers) {
        client.getPreferences().clear();
        client.getPreferences().addAll(
            teamMembers.stream()
                .filter(member -> member != client && !"Corner".equals(member.getName()))
                .sorted(Comparator.comparing(member -> mostRecentPair(client, member)))
                .collect(Collectors.toList())
        );
    }

    private void setPreferencesForPivot(TeamMember pivot, Set<TeamMember> teamMembers) {
        pivot.getPreferences().clear();
        pivot.getPreferences().addAll(
            teamMembers.stream()
                .filter(member -> member != pivot)
                .sorted(Comparator.comparing(member -> mostRecentPair(pivot, member)))
                .collect(Collectors.toList())
        );
    }

    private void setPreferencesForCorner(TeamMember corner, Set<TeamMember> teamMembers) {
        corner.getPreferences().clear();
        corner.getPreferences().addAll(
            teamMembers.stream()
                .filter(member -> member != corner && !member.isClient())
                .sorted(Comparator.comparing(member -> mostRecentPair(corner, member)))
                .collect(Collectors.toList())
        );
    }
}
