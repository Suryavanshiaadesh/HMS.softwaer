package com.ss.rooms;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_image_seq")
    @SequenceGenerator(name = "room_image_seq", sequenceName = "room_image_seq", allocationSize = 1)
    private Long id;

    @Lob
    private byte[] image;

    private String type; // bed, bathroom, gallery, etc.

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Rooms room;
}