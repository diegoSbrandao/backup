package com.apprest.services;

import com.apprest.dto.mapper.EventDTO;
import com.apprest.dto.mapper.UriDTO;
import com.apprest.entities.Event;
import com.apprest.handlerexception.EntityNotFoundException;
import com.apprest.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.net.URL;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final FileSaver fileSaver;


    @Transactional
    public Page<EventDTO> findAllPaged(PageRequest pageRequest){
        Page<Event> list = eventRepository.findAll(pageRequest);
        return list.map(dto -> new ModelMapper().map(dto, EventDTO.class));
    }

    @Transactional
    public Optional<EventDTO> findById(Long id) {
        Optional<EventDTO> eventDTO = checkIfEntityIsValid(id);

        EventDTO dto = new ModelMapper().map(eventDTO.get(), EventDTO.class);

        return Optional.of(dto);
    }

    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        try {
            eventDTO.setId(null);
            ModelMapper mapper = new ModelMapper();
            Event event = mapper.map(eventDTO, Event.class);
            event = eventRepository.save(event);
            eventDTO.setId(event.getId());
        } catch (ConstraintViolationException exceptionError) {
            throw exceptionError;
        }
        return eventDTO;
    }

    @Transactional
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Optional<EventDTO> dto = checkIfEntityIsValid(id);

        eventDTO.setId(id);

        ModelMapper mapper = new ModelMapper();

        Event event = mapper.map(eventDTO, Event.class);

        eventRepository.save(event);

        return eventDTO;

    }

    public void deleteEvent(Long id) {
        try {
            Optional<EventDTO> eventDTO = checkIfEntityIsValid(id);
            eventRepository.deleteById(id);
        } catch (
                DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity Violation");
        }

    }
    private Optional<EventDTO> checkIfEntityIsValid(Long id) {
        Optional<Event> eventIdIsValid = eventRepository.findById(id);

        if (eventIdIsValid.isEmpty()) {
            throw new EntityNotFoundException("Id Not Found! " + id);
        }
        EventDTO eventDTO = new ModelMapper().map(eventIdIsValid.get(), EventDTO.class);
        return Optional.of(eventDTO);
    }

    public UriDTO uploadFile(MultipartFile file) {
        String url = fileSaver.write(file);
        return new UriDTO(url);
    }
}
