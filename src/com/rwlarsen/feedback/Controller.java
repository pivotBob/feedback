package com.rwlarsen.feedback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private final PairGenerator pairGenerator;
    private final TeamDataStore dataStore;
    private final Team team;
    private Set<Pair> pairs = new HashSet<>();

    @FXML
    private TableView memberTable;

    @FXML
    private TableColumn<TeamMember, String> nameCol;

    @FXML
    private TableColumn<TeamMember, Boolean> activeCol;

    @FXML
    private TableColumn<TeamMember, Boolean> presentCol;

    @FXML
    private TableColumn<TeamMember, Boolean> clientCol;

    @FXML
    private TextArea textArea;

    public Controller(PairGenerator pairGenerator, TeamDataStore dataStore) {
        this.pairGenerator = pairGenerator;
        this.dataStore = dataStore;
        team = dataStore.load();
    }

    @FXML
    public void initialize() {
        memberTable.setItems(team.getMembers());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            event.getRowValue().setName(event.getNewValue());
        });
        activeCol.setCellValueFactory(row -> row.getValue().isActiveObs());
        activeCol.setCellFactory(CheckBoxTableCell.forTableColumn(activeCol));
        presentCol.setCellValueFactory(row -> row.getValue().isPresentObs());
        presentCol.setCellFactory(CheckBoxTableCell.forTableColumn(presentCol));
        clientCol.setCellValueFactory(row -> row.getValue().isClientObs());
        clientCol.setCellFactory(CheckBoxTableCell.forTableColumn(clientCol));
    }

    @FXML
    public void run() {
        Set<TeamMember> activeMembers = team.getActiveMembers();
        activeMembers.forEach(tm -> tm.resetPossibilities());
        pairs = pairGenerator.generatePairs(activeMembers);
        StringBuffer sb = new StringBuffer();
        pairs.forEach(pair -> sb.append(pair.getMember1().getName() + " <=> " + pair.getMember2().getName() + "\n"));
        textArea.setText(sb.toString());
    }

    @FXML
    public void save() {
        team.getHistory().addAll(pairs);
        dataStore.save(team);
    }

    @FXML
    public void add() {
        team.getMembers().add(new TeamMember("New Team Member",true,true,true));
    }
}
