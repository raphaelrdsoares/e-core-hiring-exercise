package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;
    private String name;
}
