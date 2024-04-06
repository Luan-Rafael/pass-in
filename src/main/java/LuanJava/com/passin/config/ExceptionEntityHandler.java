package LuanJava.com.passin.config;


import LuanJava.com.passin.domain.attendee.exceptions.AttendeNotFoundException;
import LuanJava.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import LuanJava.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import LuanJava.com.passin.domain.event.exceptions.EventIsFullException;
import LuanJava.com.passin.domain.event.exceptions.EventNotFoundException;
import LuanJava.com.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return  ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventIsFull(EventIsFullException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCheckInAlreadyExist(CheckInAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity handleNoResourceFound() {
        return ResponseEntity.notFound().build();
    }

}
