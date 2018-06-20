package com.rwlarsen.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;

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
    private SimpleBooleanProperty client;
    private SimpleBooleanProperty active;
    private SimpleBooleanProperty present;

    public TeamMember() {
        preferences = new LinkedList<>();
        possibilities = new LinkedList<>();
        client = new SimpleBooleanProperty();
        active = new SimpleBooleanProperty();
        present = new SimpleBooleanProperty();
    }

    public TeamMember(String name, boolean active, boolean present, boolean client) {
        this();
        this.name = name;
        this.active.set(active);
        this.present.set(present);
        this.client.set(client);
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


    @JsonIgnore
    public ObservableBooleanValue isClientObs() {
        return client;
    }

    public boolean isClient() {
        return client.get();
    }

    public void setClient(boolean client) {
        this.client.set(client);
    }

    @JsonIgnore
    public ObservableBooleanValue isActiveObs() {
        return active;
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    @JsonIgnore
    public ObservableBooleanValue isPresentObs() {
        return present;
    }

    public boolean isPresent() {
        return present.get();
    }

    public void setPresent(boolean present) {
        this.present.set(present);
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
        return name.equals(((TeamMember) that).name);
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

