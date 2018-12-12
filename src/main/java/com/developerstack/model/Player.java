package com.developerstack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_Sequence")
    @SequenceGenerator(name = "player_Sequence", sequenceName = "PLAYER_SEQ")
    private Long id;

    @Column(name = "name" , length = 100000)
    private String name;

    @Column(name = "num")
    private int num;

    @Column(name = "position",  length = 1000000)
    private String position;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnore
    private Team team;


    @OneToMany(mappedBy = "assignedPlayer", cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();


    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<Vote> votes = new HashSet<>();


    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getPlayers().add(this);
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setAssignedPlayer(this);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setPlayer(this);
    }

    public void removeVote(Vote next) {
        if (votes.size() >0 ){
            votes.remove(next);
        }
    }


    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "{name: '"+name +"' , position: '"+position+"'}";
    }
}
