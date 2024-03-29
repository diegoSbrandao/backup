package com.apprest.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UriDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uri;

}
