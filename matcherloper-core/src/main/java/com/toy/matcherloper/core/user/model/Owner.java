package com.toy.matcherloper.core.user.model;

import com.toy.matcherloper.core.room.model.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@DiscriminatorValue(value = "O")
@NoArgsConstructor
public class Owner extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void createRoom(Room room){
        this.room = room;
    }

    @Builder(builderMethodName = "ownerBuilder")
    public Owner(Long id, String email, String password, String name, String phoneNumber, String introduction, Set<UserPosition> userPositionSet, Set<Skill> skillSet, Address address) {
        super(id, email, password, name, phoneNumber, introduction, userPositionSet, skillSet, address);
    }
}
