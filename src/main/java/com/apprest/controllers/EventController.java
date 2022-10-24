package com.apprest.controllers;

import com.apprest.dto.handlers.EventRequest;
import com.apprest.dto.handlers.EventResponse;
import com.apprest.dto.mapper.EventDTO;
import com.apprest.dto.mapper.UriDTO;
import com.apprest.services.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@Api(value="EventoEndpoint", description = "Evento, Descrição dos Métodos",tags = {"EventoEndpoint"})
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1/eventos")
public class EventController {

    private final EventService eventService;

    @ApiOperation(value = "Retorna uma lista de todos os eventos cadastrados, ordenado por paginação")
    @GetMapping
    public ResponseEntity<Page<EventResponse>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.ASC,orderBy);
        Page<EventDTO> eventDTO = eventService.findAllPaged(pageRequest);

        ModelMapper mapper = new ModelMapper();

        Page<EventResponse> responses =  eventDTO
                .map(produtoDTO -> mapper.map(produtoDTO, EventResponse.class));

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Retorna uma lista de todos os eventos cadastrados, baseado na data crescente.")
    @GetMapping("/data")
    public ResponseEntity<Page<EventResponse>> findAllPagedPostDate(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "date") String orderBy){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.ASC,orderBy);
        Page<EventDTO> eventDTO = eventService.findAllPaged(pageRequest);

        ModelMapper mapper = new ModelMapper();

        Page<EventResponse> responses =  eventDTO
                .map(produtoDTO -> mapper.map(produtoDTO, EventResponse.class));

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Retorna um único evento pelo seu id.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<EventResponse>> findById(@PathVariable Long id){
        Optional<EventDTO> eventDTO = eventService.findById(id);
        EventResponse eventResponse = new ModelMapper().map(eventDTO.get(),EventResponse.class);
        return new ResponseEntity<>(Optional.of(eventResponse), HttpStatus.OK);
    }

    @ApiOperation(value = "Adiciona (salva) um evento.")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventResponse eventResponse){
        ModelMapper mapper = new ModelMapper();

        EventDTO produtoDTO = mapper.map(eventResponse, EventDTO.class);

        produtoDTO = eventService.createEvent(produtoDTO);

        return new ResponseEntity<>(mapper.map(produtoDTO, EventResponse.class),HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualiza um evento existente.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse>
    updateEvent(@PathVariable Long id,@Valid @RequestBody EventRequest eventRequest){

        ModelMapper mapper = new ModelMapper();

        EventDTO eventDTO = mapper.map(eventRequest, EventDTO.class);

        eventDTO = eventService.updateEvent(id,eventDTO);

        return new ResponseEntity<>(
                mapper.map(eventDTO, EventResponse.class),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta um evento existente.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Adiciona (salva) uma foto no Cloud Storage do Google.")
    @PostMapping(value = "/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file){

        UriDTO dto = eventService.uploadFile(file);

        return ResponseEntity.ok().body(dto);
    }

}