package com.apprest.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_devotional")
@Entity
public class Devotional implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "nm_devotional_Title", length = 255)
    private String devotionalTitle;

    @NotNull(message = "Campo Texto Versículo é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_text_Versicle", length = 500)
    private String textVersicle;

    @NotNull(message = "Campo Texto Devocional é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_devotional_Text", length = 500)
    private String devotionalText;

    @NotNull(message = "Campo Texto Oração é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_prayer_Text", length = 500)
    private String prayerText;

    @NotNull(message = "Campo Data da Postagem é de preenchimento obrigatório.")
    @Column(name = "dt_post_Date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date postDate;

    @NotNull(message = "Campo Hora da Postagem é de preenchimento obrigatório.")
    @Column(name = "dt_post_Time")
    @JsonFormat(pattern="HH:mm:ss")
    private Date postTime;

    @Column(name = "ds_img_URL")
    private String imgURL;

}