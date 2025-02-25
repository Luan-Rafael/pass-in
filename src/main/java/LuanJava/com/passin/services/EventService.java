package LuanJava.com.passin.services;
import LuanJava.com.passin.domain.attendee.Attendee;
import LuanJava.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import LuanJava.com.passin.domain.event.Event;
import LuanJava.com.passin.domain.event.exceptions.EventIsFullException;
import LuanJava.com.passin.domain.event.exceptions.EventNotFoundException;
import LuanJava.com.passin.dto.attendee.AttendeeIdDTO;
import LuanJava.com.passin.dto.attendee.AttendeeRequestDTO;
import LuanJava.com.passin.dto.event.EventIdDTO;
import LuanJava.com.passin.dto.event.EventRequestDTO;
import LuanJava.com.passin.dto.event.EventResponseDTO;
import LuanJava.com.passin.repositories.EventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(event.getId());
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {

        Event newEvent = new Event();

        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());

    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.validEmail(attendeeRequestDTO.email());
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = this.getEventById(eventId);

        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(event.getId());


        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventIsFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private String createSlug(String text) {
       String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
       return normalize.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "").replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-").toLowerCase();
    }

    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
    }

    private void validEmail(String email){
    //TODO: add validator email
    }

}
