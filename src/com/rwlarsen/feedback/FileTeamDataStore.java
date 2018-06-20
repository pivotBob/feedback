package com.rwlarsen.feedback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.File;
import java.io.IOException;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return team;
    }
}
