package LuanJava.com.passin.services;

import LuanJava.com.passin.domain.attendee.Attendee;
import LuanJava.com.passin.domain.checkin.CheckIn;
import LuanJava.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import LuanJava.com.passin.repositories.CheckInRepository;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(newCheckIn);
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
    private void verifyCheckInExists(String attendeeId)    {
        Optional<CheckIn> isCheckedIn = this.checkInRepository.findByAttendeeId(attendeeId);

        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
    }
}
