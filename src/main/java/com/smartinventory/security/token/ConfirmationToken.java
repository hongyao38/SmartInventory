package com.smartinventory.security.token;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.smartinventory.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "confirmation_sequence", 
                        sequenceName = "confirmation_sequence", 
                        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                        generator = "confirmation_token_sequence")
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationToken(LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        token = UUID.randomUUID().toString();
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
