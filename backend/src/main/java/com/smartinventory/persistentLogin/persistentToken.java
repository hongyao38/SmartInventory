package com.smartinventory.persistentLogin;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class persistentToken {
    @Id
    @NotNull @Size(max = 64)
    private String series;

    @NotNull @Size(max = 64)
    private String username;

    @NotNull @Size(max = 64)
    private String token;

    @NotNull
    private Timestamp last_used;
}
