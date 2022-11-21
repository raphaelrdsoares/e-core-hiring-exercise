package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.repository;

import java.sql.Timestamp;
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
    public Timestamp createdAt;
    public Timestamp updatedAt;
}
