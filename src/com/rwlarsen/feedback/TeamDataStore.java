package com.rwlarsen.feedback;

public interface TeamDataStore {
    Team load();
    void save(Team team);
}
