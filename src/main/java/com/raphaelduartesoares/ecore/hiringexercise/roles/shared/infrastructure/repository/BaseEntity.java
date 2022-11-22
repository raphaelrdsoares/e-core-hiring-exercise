package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    public UUID id;
    public Timestamp createdAt;
    public Timestamp updatedAt;
}
