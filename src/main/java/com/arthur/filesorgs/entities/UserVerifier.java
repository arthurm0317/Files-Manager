package com.arthur.filesorgs.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "NPL_USER_VERIFIER")
public class UserVerifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", unique = true)
    private User user;

    @Column(nullable = false)
    private Instant data_expiration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getData_expiration() {
        return data_expiration;
    }

    public void setData_expiration(Instant data_expiration) {
        this.data_expiration = data_expiration;
    }

    @Override
    public String toString() {
        return "UserVerifier{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", user=" + user +
                ", data_expiration=" + data_expiration +
                '}';
    }
}
