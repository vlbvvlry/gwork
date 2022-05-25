package com.iem.iemserver.controllers;

import com.iem.iemserver.models.Event;
import com.iem.iemserver.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/get_all_events")
    public @ResponseBody Iterable<Event> getAllEvent(){
        return eventRepository.findAll();
    }

    @GetMapping("/get_all_events/{dep_id}")
    public @ResponseBody Iterable<Event> getAllEventsByDepartmentId
            (@PathVariable("dep_id") Integer departmentId) {
        return eventRepository.findAllByDepartmentId(departmentId);
    }

    @PostMapping("/events/add")
    public void addEvent(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam Integer userId) {
        Event event = new Event();
        event.setTitle(title);
        event.setContent(content);
        event.setDate(new Date());
        event.setUserId(userId);
        //!!!
        event.setDepartmentId(0);
        //
        eventRepository.save(event);
    }

}
