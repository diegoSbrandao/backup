package com.apprest.controllers;

import com.apprest.dto.handlers.DevotionalRequest;
import com.apprest.dto.handlers.DevotionalResponse;
import com.apprest.dto.mapper.DevotionalDTO;
import com.apprest.dto.mapper.UriDTO;
import com.apprest.services.DevotionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@Api(value="DevocionalEndpoint", description = "Devocional, Descrição dos Métodos",tags = {"DevocionalEndpoint"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/devocional")
public class DevotionalController {
    
    private final DevotionalService devotionalService;


    @ApiOperation(value = "Retorna uma lista de todos os devocionais cadastrados, ordenado por paginação")
    @GetMapping
    public ResponseEntity<Page<DevotionalResponse>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.ASC,orderBy);
        Page<DevotionalDTO> devotionalDTO = devotionalService.findAllPaged(pageRequest);

        ModelMapper mapper = new ModelMapper();

        Page<DevotionalResponse> responses =  devotionalDTO
                .map(produtoDTO -> mapper.map(produtoDTO, DevotionalResponse.class));

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Retorna uma lista de todos os devocionais cadastrados, baseado na data crescente.")
    @GetMapping("/data")
    public ResponseEntity<Page<DevotionalResponse>> findAllPagedPostDate(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "postDate") String orderBy){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.ASC,orderBy);
        Page<DevotionalDTO> devotionalDTO = devotionalService.findAllPaged(pageRequest);

        ModelMapper mapper = new ModelMapper();

        Page<DevotionalResponse> responses =  devotionalDTO
                .map(produtoDTO -> mapper.map(produtoDTO, DevotionalResponse.class));

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Retorna um único devocional pelo seu id.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DevotionalResponse>> findById(@PathVariable Long id){
        Optional<DevotionalDTO> devotionalDTO = devotionalService.findById(id);
        DevotionalResponse devotionalResponse = new ModelMapper().map(devotionalDTO.get(),DevotionalResponse.class);
        return new ResponseEntity<>(Optional.of(devotionalResponse), HttpStatus.OK);
    }

    @ApiOperation(value = "Adiciona (salva) um devocional.")
    @PostMapping()
    public ResponseEntity<DevotionalResponse> createDevotional(@Valid @RequestBody DevotionalResponse devotionalResponse){
        ModelMapper mapper = new ModelMapper();

        DevotionalDTO produtoDTO = mapper.map(devotionalResponse, DevotionalDTO.class);

        produtoDTO = devotionalService.createDevotional(produtoDTO);

        return new ResponseEntity<>(mapper.map(produtoDTO, DevotionalResponse.class),HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualiza um devocional existente.")
    @PutMapping("/{id}")
    public ResponseEntity<DevotionalResponse>
    updateDevotional(@PathVariable Long id,@Valid @RequestBody DevotionalRequest devotionalRequest){

        ModelMapper mapper = new ModelMapper();

        DevotionalDTO devotionalDTO = mapper.map(devotionalRequest, DevotionalDTO.class);

        devotionalDTO = devotionalService.updateDevotional(id,devotionalDTO);

        return new ResponseEntity<>(
                mapper.map(devotionalDTO, DevotionalResponse.class),
                HttpStatus.OK);
    }
    
    @ApiOperation(value = "Deleta um devocional existente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevotional(@PathVariable Long id){
        devotionalService.deleteDevotional(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Adiciona (salva) uma foto no Cloud Storage do Google.")
    @PostMapping(value = "/image")
    public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file){

        UriDTO dto = devotionalService.uploadFile(file);

        return ResponseEntity.ok().body(dto);
    }

}