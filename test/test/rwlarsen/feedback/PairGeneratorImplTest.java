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
    TeamMember tm1;
    TeamMember tm2;
    TeamMember tm3;
    TeamMember tm4;
    TeamMember tm5;
    TeamMember tm6;
    Set<TeamMember> members;

    @BeforeEach
    void setUp() {
        subject = new PairGeneratorImpl();
        tm1 = new TeamMember("1", true, true);
        tm2 = new TeamMember("2", true, true);
        tm3 = new TeamMember("3", true, true);
        tm4 = new TeamMember("4", true, true);
        tm5 = new TeamMember("5", true, true);
        tm6 = new TeamMember("6", true, true);
        members = new TreeSet<>();
        members.add(tm1);
        members.add(tm2);
        members.add(tm3);
        members.add(tm4);
        members.add(tm5);
        members.add(tm6);
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
        tm1.setPreferences(tm2, tm3, tm4, tm5, tm6);
        tm2.setPreferences(tm1, tm4, tm5, tm6, tm3);
        tm3.setPreferences(tm4, tm5, tm6, tm1, tm2);
        tm4.setPreferences(tm3, tm6, tm1, tm2, tm5);
        tm5.setPreferences(tm6, tm1, tm2, tm3, tm4);
        tm6.setPreferences(tm5, tm2, tm3, tm4, tm1);

        Set<Pair> result = subject.generatePairs(members);

        validate(result);
    }

    @Test
    public void circularMatch() {
        tm1.setPreferences(tm2, tm3, tm4, tm5, tm6);
        tm2.setPreferences(tm3, tm4, tm5, tm6, tm1);
        tm3.setPreferences(tm4, tm5, tm6, tm1, tm2);
        tm4.setPreferences(tm5, tm6, tm1, tm2, tm3);
        tm5.setPreferences(tm6, tm1, tm2, tm3, tm4);
        tm6.setPreferences(tm1, tm2, tm3, tm4, tm5);

        Set<Pair> result = subject.generatePairs(members);

        validate(result);
    }

    @Test
    public void complexMatch() {
        tm1.setPreferences(tm3, tm4, tm2, tm6, tm5);
        tm2.setPreferences(tm6, tm5, tm4, tm1, tm3);
        tm3.setPreferences(tm2, tm4, tm5, tm1, tm6);
        tm4.setPreferences(tm5, tm2, tm3, tm6, tm1);
        tm5.setPreferences(tm3, tm1, tm2, tm4, tm6);
        tm6.setPreferences(tm5, tm1, tm3, tm4, tm2);

        Set<Pair> result = subject.generatePairs(members);

        validate(result);
    }
}