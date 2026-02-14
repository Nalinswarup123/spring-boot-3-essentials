package com.example.roomwebapp.web.controller;

import com.example.roomwebapp.data.entity.RoomEntity;
import com.example.roomwebapp.data.repository.RoomRepository;
import com.example.roomwebapp.service.RoomService;
import com.example.roomwebapp.web.model.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public String getRoomPage(Model model) {
        model.addAttribute("rooms", service.getAllRooms());
        return "rooms";
    }
}
