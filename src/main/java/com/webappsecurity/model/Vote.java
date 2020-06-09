package com.webappsecurity.model;


import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "vote_Sequence")
    @SequenceGenerator(name = "vote_Sequence", sequenceName = "VOTE_SEQ")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;


    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
