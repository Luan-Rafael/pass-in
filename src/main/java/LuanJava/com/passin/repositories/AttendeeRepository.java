package LuanJava.com.passin.repositories;

import LuanJava.com.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository  extends JpaRepository<Attendee, String> {


     List<Attendee> findByEventId(String id);
     Optional<Attendee> findByEventIdAndEmail(String eventId, String email);
}
