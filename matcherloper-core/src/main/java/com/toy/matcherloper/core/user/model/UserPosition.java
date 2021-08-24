package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.user.model.type.PositionType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"type"})
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

    public UserPosition(PositionType type) {
        this.type = type;
    }

    public void changeUser(User user) {
        this.user = user;
    }
}
