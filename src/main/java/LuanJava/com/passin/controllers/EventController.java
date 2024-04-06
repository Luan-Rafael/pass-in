package LuanJava.com.passin.controllers;


import LuanJava.com.passin.dto.attendee.AttendeeIdDTO;
import LuanJava.com.passin.dto.attendee.AttendeeRequestDTO;
import LuanJava.com.passin.dto.attendee.AttendeesListResponseDTO;
import LuanJava.com.passin.dto.event.EventIdDTO;
import LuanJava.com.passin.dto.event.EventRequestDTO;
import LuanJava.com.passin.dto.event.EventResponseDTO;
import LuanJava.com.passin.services.AttendeeService;
import LuanJava.com.passin.services.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {


    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO > createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }
    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO attendeesListResponseDTO = this.attendeeService.getEventsAttendee(id);
        return  ResponseEntity.ok().body(attendeesListResponseDTO);

    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO > registerAttendee(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }
}
