package org.example.orgflow.response;


import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<T> {

    T content;
    List<ApiMessages> messages;


}
