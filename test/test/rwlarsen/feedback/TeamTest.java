package test.rwlarsen.feedback;

import com.rwlarsen.feedback.Pair;
import com.rwlarsen.feedback.Team;
import com.rwlarsen.feedback.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private Team team;
    private TeamMember a;
    private TeamMember b;
    private TeamMember c;
    private TeamMember d;
    private TeamMember e;
    private TeamMember f;

    @BeforeEach
    void setup() {
        team = new Team();
        a = new TeamMember("A", true, true,true);
        b = new TeamMember("B", true, true,false);
        c = new TeamMember("C", true, true,true);
        d = new TeamMember("D", true, true,false);
        e = new TeamMember("E", false,true, true);
        f = new TeamMember("F", true, false,true);
        team.getMembers().add(a);
        team.getMembers().add(b);
        team.getMembers().add(c);
        team.getMembers().add(d);
        team.getMembers().add(e);
        team.getMembers().add(f);
    }


    @Test
    void getActiveMembersReturnsMembers() {

        Set<TeamMember> result = team.getActiveMembers();

        assertTrue(result.contains(a));
        assertTrue(result.contains(b));
        assertTrue(result.contains(c));
        assertTrue(result.contains(d));
    }


    @Test
    void getActiveMembersDoesNotReturnInactive() {

        Set<TeamMember> result = team.getActiveMembers();

        assertFalse(result.contains(e));
    }

    @Test
    void getActiveMembersDoesNotReturnAbsent() {

        Set<TeamMember> result = team.getActiveMembers();

        assertFalse(result.contains(f));
    }

    @Test
    void getActiveMembersAddsCornerWhenOdd() {
        team.getMembers().remove(a);

        Set<TeamMember> result = team.getActiveMembers();

        assertTrue(result.stream().anyMatch(teamMember -> "Corner".equals(teamMember.getName())));
    }

    @Test
    void getActiveMembersAddsCornerToPivots() {
        team.getMembers().remove(a);

        team.getActiveMembers();

        assertTrue(b.getPreferences().stream().anyMatch(teamMember -> "Corner".equals(teamMember.getName())));
        assertTrue(d.getPreferences().stream().anyMatch(teamMember -> "Corner".equals(teamMember.getName())));
    }

    @Test
    void getActiveMembersDoesNotAddCornerToClients() {
        team.getMembers().remove(a);

        team.getActiveMembers();

        assertTrue(c.getPreferences().stream().noneMatch(teamMember -> "Corner".equals(teamMember.getName())));
    }

    @Test
    void getActiveMembersSortsPreferencesByDates() {
        LocalDate lastWeek = LocalDate.now().minusWeeks(1);
        LocalDate weekBeforeLast = LocalDate.now().minusWeeks(2);
        team.getHistory().add(new Pair(a,b, lastWeek));
        team.getHistory().add(new Pair(c,d, lastWeek));
        team.getHistory().add(new Pair(a,c, weekBeforeLast));
        team.getHistory().add(new Pair(b,d, weekBeforeLast));

        team.getActiveMembers();

        assertEquals(a.getPreferences().get(0),d);
        assertEquals(a.getPreferences().get(1),c);
        assertEquals(a.getPreferences().get(2),b);

        assertEquals(b.getPreferences().get(0),c);
        assertEquals(b.getPreferences().get(1),d);
        assertEquals(b.getPreferences().get(2),a);

        assertEquals(c.getPreferences().get(0),b);
        assertEquals(c.getPreferences().get(1),a);
        assertEquals(c.getPreferences().get(2),d);

        assertEquals(d.getPreferences().get(0),a);
        assertEquals(d.getPreferences().get(1),b);
        assertEquals(d.getPreferences().get(2),c);
    }
}