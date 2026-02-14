package com.example.roomwebapp.async;

import com.example.roomwebapp.service.RoomService;
import com.example.roomwebapp.web.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RoomCleanerListner {
    private static final Logger LOG = LoggerFactory.getLogger(RoomCleanerListner.class);

    private final ObjectMapper mapper;
    private final RoomService roomService;

    public RoomCleanerListner(ObjectMapper mapper, RoomService roomService) {
        this.mapper = mapper;
        this.roomService = roomService;
    }

    public void receiveMessage(String message) {
        try {
            AsyncPayload payload = mapper.readValue(message, AsyncPayload.class);
            if ("ROOM".equals(payload.getModel())) {
                Room room = roomService.getRoomById(payload.getId());
                LOG.info("Room {}:{} needs to be cleaned.", room.getNumber(), room.getName());
            } else {
                LOG.warn("Unknow model type.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
