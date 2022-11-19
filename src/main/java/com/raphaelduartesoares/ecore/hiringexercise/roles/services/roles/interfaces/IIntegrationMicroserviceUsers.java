package com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.interfaces;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.integrations.microservices.users.dtos.ResponseUserDto;
import java.util.List;

public interface IIntegrationMicroserviceUsers {
  public List<ResponseUserDto> getUsers();

  public ResponseUserDto getUserById(String id);
}
