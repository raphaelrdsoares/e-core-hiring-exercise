package com.raphaelduartesoares.ecore.hiringexercise.roles.api.rest.roles.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseMembershipDto {
    @Schema(example = "dev")
    public String roleCode;

    @Schema(example = "83b239d5-d81e-5c7a-9ed3-7d4d0f9fc218")
    public String userId;

    @Schema(example = "5693b2b3-8d10-5bdc-bc35-7f5875ad66c0")
    public String teamId;
}
