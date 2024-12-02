package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.announcement.auxiliary.enums.ChatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "TB_CHAT")
public class Chat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_announcement", nullable = false)
    private Announcement announcement;

    @Column(nullable = false)
    private ChatStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    @Column(nullable = false)
    private Timestamp dateOpen;

    private Timestamp dateDeleted;

    private Timestamp dateClose;

    public Chat() {
        this.dateOpen = new Timestamp(System.currentTimeMillis());
        this.status = ChatStatus.OPEN;
    }

    public Chat(User user, Announcement announcement) {
        this.dateOpen = new Timestamp(System.currentTimeMillis());
        this.status = ChatStatus.OPEN;
        this.user = user;
        this.announcement = announcement;
    }
}
