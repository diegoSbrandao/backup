package com.apprest.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile file;
}
