package com.apprest.dto.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventRequest {


    @NotNull(message = "Campo nome é de preenchimento obrigatório.")
    @NotBlank(message = "Campo nome é de preenchimento obrigatório.")
    @Column(name = "nm_event")
    private String name;

    @NotNull(message = "Campo local é de preenchimento obrigatório.")
    @NotBlank(message = "Campo nome é de preenchimento obrigatório.")
    @Column(name = "nm_place")
    private String place;

    @NotNull(message = "Campo data é de preenchimento obrigatório.")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "dt_event_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;

    @NotNull(message = "Campo horário é de preenchimento obrigatório.")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "ds_event_time")
    @JsonFormat(pattern="HH:mm:ss")
    private Date time;

    @Column(name = "ds_img_URL")
    private String imgURL;

}


