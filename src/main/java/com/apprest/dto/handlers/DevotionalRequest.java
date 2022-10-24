package com.apprest.dto.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevotionalRequest {

    @NotNull(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "nm_devotional_Title", length = 255)
    private String devotionalTitle;

    @NotNull(message = "Campo Texto Versículo é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_text_Versicle", columnDefinition = "TEXT")
    private String textVersicle;

    @NotNull(message = "Campo Texto Devocional é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_devotional_Text", columnDefinition = "TEXT")
    private String devotionalText;

    @NotNull(message = "Campo Texto Oração é de preenchimento obrigatório.")
    @NotBlank(message = "Campo Título Devocional é de preenchimento obrigatório.")
    @Column(name = "ds_prayer_Text", columnDefinition = "TEXT")
    private String prayerText;

    @NotNull(message = "Campo Data da Postagem é de preenchimento obrigatório.")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "dt_post_Date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date postDate;

    @NotNull(message = "Campo Hora da Postagem é de preenchimento obrigatório.")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", name = "dt_post_Time")
    @JsonFormat(pattern="HH:mm:ss")
    private Date postTime;

    @Column(name = "ds_img_URL")
    private String imgURL;


}
