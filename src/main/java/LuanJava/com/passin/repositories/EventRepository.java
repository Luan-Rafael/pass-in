package LuanJava.com.passin.repositories;

import LuanJava.com.passin.domain.event.Event;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event, String> {

}
