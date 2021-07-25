package com.toy.matcherloper.core.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_position_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PositionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //constructor
    public UserPosition(PositionType type) {
        this.type = type;
    }

    //method
    public void changeUser(User user) {
        this.user = user;
    }
}
