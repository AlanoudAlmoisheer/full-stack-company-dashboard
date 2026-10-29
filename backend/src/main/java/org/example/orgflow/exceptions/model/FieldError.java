package org.example.orgflow.exceptions.model;


import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class FieldError implements Serializable {
    String filedName;
    String nameAr;
    String nameEn;
}
