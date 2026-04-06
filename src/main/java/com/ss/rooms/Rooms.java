package com.ss.rooms;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "rooms_seq", allocationSize = 1)
    private Long roomId;

    private String roomNumber;
    private String type;
    private Double price;
    private Integer occupied;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images = new ArrayList<>();

    public int getCapacity() {
        switch (this.type.toLowerCase()) {
            case "single": return 1;
            case "double": return 2;
            case "triple": return 3;
            default: return 1;
        }
    }

    @Transient
    public int getAvailableBeds() {
        return getCapacity() - (occupied == null ? 0 : occupied);
    }
}