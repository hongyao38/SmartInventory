package com.smartinventory.security.config;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class persistent_logins {
    @Id
    @Column(nullable = false)
    private String series;

    @Column(nullable = false)
    private String username;

    @Column(nullable = faslse)
    private String token;

    @Column(nullable = false)
    private Timestamp last_used;
}
