package com.rwlarsen.feedback;

import java.time.LocalDate;

public class Pair implements Comparable<Pair>{
    private TeamMember member1;
    private TeamMember member2;
    private LocalDate date;

    public Pair() {
    }

    public Pair(TeamMember member1, TeamMember member2, LocalDate date) {
        this.member1 = member1;
        this.member2 = member2;
        this.date = date;
    }

    public boolean contains(TeamMember query) {
        return query.equals(member1) || query.equals(member2);
    }

    public TeamMember getMember1() {
        return member1;
    }

    public TeamMember getMember2() {
        return member2;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Pair that) {
        return member1.compareTo(that.member1);
    }
}
