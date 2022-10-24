package com.apprest.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevotionalDTO {

    private Long id;
    private String devotionalTitle;
    private String textVersicle;
    private String devotionalText;
    private String prayerText;
    private Date postDate;
    private Date postTime;
    private String imgURL;


}
