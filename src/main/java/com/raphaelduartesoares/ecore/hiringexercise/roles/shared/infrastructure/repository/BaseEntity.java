package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public abstract class BaseEntity {
    public UUID id;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
