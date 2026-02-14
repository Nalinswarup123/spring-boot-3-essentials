package com.example.roomwebapp.service;

import com.example.roomwebapp.data.entity.RoomEntity;
import com.example.roomwebapp.data.repository.RoomRepository;
import com.example.roomwebapp.web.model.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAllRooms() {
        List<RoomEntity> entities = repository.findAll();
        List<Room> rooms = new ArrayList<>();
        entities.forEach(e -> rooms.add(getRoomFromEntity(e)));
        return rooms;
    }

    public Room getRoomById(UUID id) {
        Optional<RoomEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        return getRoomFromEntity(entity.get());
    }

    public Room addRoom(Room room) {
        RoomEntity entity = getRoomEntityFromRoom(room);
        RoomEntity saved = repository.save(entity);
        return getRoomFromEntity(saved);
    }

    public Room updateRoom(Room room) {
        RoomEntity entity = getRoomEntityFromRoom(room);
        RoomEntity saved = repository.save(entity);
        return getRoomFromEntity(saved);
    }

    public void deleteRoom(UUID id) {
        repository.deleteById(id);
    }

    private Room getRoomFromEntity(RoomEntity entity) {
        return new Room(entity.getRoomId(), entity.getName(), entity.getNumber(), entity.getBedInfo());
    }

    private RoomEntity getRoomEntityFromRoom(Room room) {
        return new RoomEntity(room.getId(), room.getName(), room.getNumber(), room.getInfo());
    }


}
