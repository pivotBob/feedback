package com.rwlarsen.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TeamMember implements Comparable<TeamMember> {
    private String name;
    @JsonIgnore
    private TeamMember currentPair;
    @JsonIgnore
    private List<TeamMember> preferences;
    @JsonIgnore
    private List<TeamMember> possibilities;
    private boolean isActive;
    private boolean isPresent;

    public TeamMember() {
        this.preferences = new LinkedList<>();
        this.possibilities = new LinkedList<>();
    }

    public TeamMember(String name, boolean isActive, boolean isPresent) {
        this.name = name;
        this.preferences = new LinkedList<>();
        this.possibilities = new LinkedList<>();
        this.isActive = isActive;
        this.isPresent = isPresent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamMember getCurrentPair() {
        return currentPair;
    }

    public void setCurrentPair(TeamMember currentPair) {
        this.currentPair = currentPair;
    }

    public List<TeamMember> getPreferences() {
        return preferences;
    }

    public List<TeamMember> getPossibilities() {
        return possibilities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setPreferences(TeamMember... prefs) {
        preferences.clear();
        preferences.addAll(Arrays.asList(prefs));
        resetPossibilities();
    }

    public void resetPossibilities() {
        possibilities.clear();
        possibilities.addAll(preferences);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof TeamMember)) {
            return false;
        }
        return name.equalsIgnoreCase(((TeamMember) that).name);
    }

    @Override
    public int compareTo(TeamMember that) {
        return this.name.compareTo(that.name);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name).append(" => ");
        if (currentPair != null) {
            sb.append(currentPair.name).append(" : ");
        } else {
            sb.append("X : ");
        }
        preferences.forEach(tm -> sb.append(tm.name).append(" "));
        return sb.toString();
    }
}

