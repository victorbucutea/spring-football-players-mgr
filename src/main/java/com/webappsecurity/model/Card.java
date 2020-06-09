package com.webappsecurity.model;
import javax.persistence.*;
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "card_Sequence")
    @SequenceGenerator(name = "card_Sequence", sequenceName = "CARD_SEQ")
    private Long id;

    private CardType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private Player assignedPlayer;

    public Card() {

    }

    public Card(CardType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setAssignedPlayer(Player assignedPlayer) {
        this.assignedPlayer = assignedPlayer;
    }
}
