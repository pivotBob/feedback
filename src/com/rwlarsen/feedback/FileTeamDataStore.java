package com.rwlarsen.feedback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class FileTeamDataStore implements TeamDataStore {

    @Override
    public void save(Team team) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            objectMapper.writeValue(new File("team.json"), team);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team load() {
        Team team = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            team = objectMapper.readValue(new File("team.json"), Team.class);
            setPreferences(team);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return team;
    }

    private void setPreferences(Team team) {
        Set<TeamMember> activeMembers = team.getMembers().stream()
                .filter(tm -> tm.isActive() && tm.isPresent())
                .collect(Collectors.toSet());
        activeMembers.forEach(tm -> {
            tm.getPreferences().addAll(activeMembers.stream()
                    .filter(member -> member != tm)
                    .sorted(Comparator.comparing(member -> mostRecentPair(tm, member, team)))
                    .collect(Collectors.toList()));
            tm.resetPossibilities();
        });
    }

    private LocalDate mostRecentPair(TeamMember a, TeamMember b, Team team) {
        return team.getHistory().stream()
                .filter(pair -> pair.contains(a) && pair.contains(b))
                .sorted(Comparator.comparing(Pair::getDate).reversed())
                .map(pair -> pair.getDate())
                .findFirst().orElse(LocalDate.of(2000, 01, 01));
    }
}
