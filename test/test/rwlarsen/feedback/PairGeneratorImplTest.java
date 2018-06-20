package test.rwlarsen.feedback;

import com.rwlarsen.feedback.Pair;
import com.rwlarsen.feedback.PairGenerator;
import com.rwlarsen.feedback.PairGeneratorImpl;
import com.rwlarsen.feedback.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class PairGeneratorImplTest {

    PairGenerator subject;
    TeamMember pivot1;
    TeamMember pivot2;
    TeamMember client3;
    TeamMember pivot4;
    TeamMember client5;
    TeamMember corner6;
    Set<TeamMember> members;

    @BeforeEach
    void setUp() {
        subject = new PairGeneratorImpl();
        pivot1 = new TeamMember("p1", true, true, false);
        pivot2 = new TeamMember("p2", true, true, false);
        client3 = new TeamMember("c3", true, true, true);
        pivot4 = new TeamMember("p4", true, true, false);
        client5 = new TeamMember("c5", true, true, true);
        corner6 = new TeamMember("x6", true, true, false);
        members = new TreeSet<>();
        members.add(pivot1);
        members.add(pivot2);
        members.add(client3);
        members.add(pivot4);
        members.add(client5);
        members.add(corner6);
    }

    private boolean prefers(TeamMember subjectMember, TeamMember newPartner) {
        List<TeamMember> prefs = subjectMember.getPreferences();
        return prefs.indexOf(newPartner) < prefs.indexOf(subjectMember.getCurrentPair());
    }

    private void validate(Set<Pair> pairs) {
        members.forEach(subjectMember -> {
            TeamMember partner = subjectMember.getCurrentPair();
            for (int i = 0; i < subjectMember.getPreferences().indexOf(partner); i++) {
                assertFalse(prefers(subjectMember.getPreferences().get(i), subjectMember));
            }
        });
    }

    @Test
    public void firstChoiceMatch() {
        pivot1.setPreferences(client5, client3, pivot4, pivot2, corner6);
        pivot2.setPreferences(corner6, pivot4, client5, pivot1, client3);
        client3.setPreferences(pivot4, client5, pivot1, pivot2);
        pivot4.setPreferences(client3, corner6, pivot1, pivot2, client5);
        client5.setPreferences(pivot1, pivot2, client3, pivot4);
        corner6.setPreferences(pivot2, pivot4, pivot1);

        Set<Pair> result = subject.generatePairs(members);

        validate(result);
    }

    @Test
    public void complexMatch() {
        pivot1.setPreferences(client3, pivot4, pivot2, corner6, client5);
        pivot2.setPreferences(corner6, client5, pivot4, pivot1, client3);
        client3.setPreferences(pivot2, pivot4, client5, pivot1);
        pivot4.setPreferences(client5, pivot2, client3, corner6, pivot1);
        client5.setPreferences(client3, pivot1, pivot2, pivot4);
        corner6.setPreferences(pivot1, pivot4, pivot2);

        Set<Pair> result = subject.generatePairs(members);

        validate(result);
    }
}