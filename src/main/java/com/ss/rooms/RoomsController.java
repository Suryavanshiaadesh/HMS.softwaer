package com.ss.rooms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class RoomsController {
	
	@Autowired
	RoomsService roomsService;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @PostMapping("/saveRoom")
    public ResponseEntity<String> saveRoom(
            @RequestParam String roomNumber,
            @RequestParam String type,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("imageTypes") List<String> imageTypes) {

        try {
            if (images.size() != imageTypes.size()) {
                return ResponseEntity.badRequest().body("Images and types count mismatch");
            }

            Rooms room = new Rooms();
            room.setRoomNumber(roomNumber);
            room.setType(type);

            // Set price based on type
            switch (type.toLowerCase()) {
                case "single" -> room.setPrice(9000.0);
                case "double" -> room.setPrice(7500.0);
                case "triple" -> room.setPrice(6500.0);
                default -> room.setPrice(0.0);
            }

            // Add images
            for (int i = 0; i < images.size(); i++) {
                MultipartFile file = images.get(i);
                if (!file.isEmpty()) {
                    RoomImage image = new RoomImage();
                    image.setImage(file.getBytes());
                    image.setType(imageTypes.get(i));
                    image.setRoom(room); // important!
                    room.getImages().add(image);
                }
            }

            roomsRepository.save(room); // cascade saves images automatically
            return ResponseEntity.ok("Room saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving room: " + e.getMessage());
        }
    }
    
    @GetMapping("/getAllRooms")
    @ResponseBody
    public List<Rooms> getAllRooms() {
        return roomsRepository.findAll();
    }
    
    @GetMapping("/getVacantRooms")
    @ResponseBody
    public ResponseEntity<List<Rooms>> getVacontRooms(){
    	return ResponseEntity.ok(roomsService.getVacantRooms());
    }
    @GetMapping("/getCountofVacantRooms")
    @ResponseBody
    public Long getCountOfVacontRooms(){
    	return roomsService.vacontCount();
    }
    
    @GetMapping("/getCountOfRooms")
    @ResponseBody
    public Long countOfAllRooms() {
    	 return roomsService.totalRooms();
    }
    
    
}