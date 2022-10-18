package com.apprest.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_event")
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Campo nome é de preenchimento obrigatório.")
    @NotBlank(message = "Campo nome é de preenchimento obrigatório.")
    @Column(name = "nm_event")
    private String name;

    @NotNull(message = "Campo local é de preenchimento obrigatório.")
    @NotBlank(message = "Campo nome é de preenchimento obrigatório.")
    @Column(name = "nm_place")
    private String place;

    @NotNull(message = "Campo data é de preenchimento obrigatório.")
    @Column(name = "dt_event_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;

    @NotNull(message = "Campo horário é de preenchimento obrigatório.")
    @Column(name = "ds_event_time")
    @JsonFormat(pattern="HH:mm:ss")
    private Date time;

    @Column(name = "ds_img_URL")
    private String imgURL;

}
