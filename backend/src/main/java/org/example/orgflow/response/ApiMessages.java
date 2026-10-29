package org.example.orgflow.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.example.orgflow.config.ApiMessagesSerializer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize(using = ApiMessagesSerializer.class)
public class ApiMessages {
    private String code;
    private String messageEn;
    private String messageAr;


}
