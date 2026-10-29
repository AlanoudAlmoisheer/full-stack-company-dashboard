package org.example.orgflow.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {


    private String errorEn;
    private String errorAr;

    public Error(String errorEn) {
        this.errorEn = errorEn;
    }
}
