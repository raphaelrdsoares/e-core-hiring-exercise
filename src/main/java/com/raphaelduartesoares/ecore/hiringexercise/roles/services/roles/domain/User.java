package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  private UUID id;
  private String name;
}
