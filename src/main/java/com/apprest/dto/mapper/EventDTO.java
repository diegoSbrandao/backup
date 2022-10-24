package com.apprest.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO  {

    private Long id;
    private String name;
    private String place;
    private Date date;
    private Date time;
    private String imgURL;

}
