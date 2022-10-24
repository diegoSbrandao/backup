package com.apprest.services;

import com.apprest.dto.mapper.DevotionalDTO;
import com.apprest.dto.mapper.UriDTO;
import com.apprest.entities.Devotional;
import com.apprest.handlerexception.EntityNotFoundException;
import com.apprest.repositories.DevotionalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DevotionalService {

    private final DevotionalRepository devotionalRepository;
    private final FileSaver fileSaver;


    @Transactional
    public Page<DevotionalDTO> findAllPaged(PageRequest pageRequest){
        Page<Devotional> list = devotionalRepository.findAll(pageRequest);
        return list.map(dto -> new ModelMapper().map(dto, DevotionalDTO.class));
    }

    @Transactional
    public Optional<DevotionalDTO> findById(Long id) {
        Optional<DevotionalDTO> devotionalDTO = checkIfEntityIsValid(id);

        DevotionalDTO dto = new ModelMapper().map(devotionalDTO.get(),DevotionalDTO.class);

        return Optional.of(dto);
    }

    @Transactional
    public DevotionalDTO createDevotional(DevotionalDTO devotionalDTO) {
        try {
            devotionalDTO.setId(null);
            ModelMapper mapper = new ModelMapper();
            Devotional devotional = mapper.map(devotionalDTO, Devotional.class);
            devotional = devotionalRepository.save(devotional);
            devotionalDTO.setId(devotional.getId());
        } catch (ConstraintViolationException exceptionError) {
            throw exceptionError;
        }
        return devotionalDTO;
    }

    @Transactional
    public DevotionalDTO updateDevotional(Long id, DevotionalDTO devotionalDTO) {
        Optional<DevotionalDTO> dto = checkIfEntityIsValid(id);

        devotionalDTO.setId(id);

        ModelMapper mapper = new ModelMapper();

        Devotional devotional = mapper.map(devotionalDTO, Devotional.class);

        devotionalRepository.save(devotional);

        return devotionalDTO;

    }

    public void deleteDevotional(Long id) {
        try {
            Optional<DevotionalDTO> devotionalDTO = checkIfEntityIsValid(id);
            devotionalRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity Violation");
        }
    }
    private Optional<DevotionalDTO> checkIfEntityIsValid(Long id) {
        Optional<Devotional> devotionalIdIsValid = devotionalRepository.findById(id);

        if(devotionalIdIsValid.isEmpty()){
            throw new EntityNotFoundException("Id Not Found! " + id);
        }
        DevotionalDTO devotionalDTO = new ModelMapper().map(devotionalIdIsValid.get(),DevotionalDTO.class);
        return Optional.of(devotionalDTO);
    }

    public UriDTO uploadFile(MultipartFile file) {
        String url = fileSaver.write(file);
        return new UriDTO(url);
    }
}
