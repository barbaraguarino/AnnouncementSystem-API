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

    @Column(nullable = false)
    private ChatStatus status;

    @Column(nullable = false)
    private Timestamp dateOpen;

    private Timestamp dateDeleted;

    private Timestamp dateClose;

    @Column(nullable = false)
    private Timestamp dateLastMessage;

    @Column(nullable = false)
    private Boolean isEvaluatedByAdvertiser;

    @Column(nullable = false)
    private Boolean isEvaluatedByUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_advertiser", nullable = false)
    private User advertiser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_announcement", nullable = false)
    private Announcement announcement;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private Set<Assessment> assessments = new HashSet<>();

    public Chat() {
        this.dateOpen = new Timestamp(System.currentTimeMillis());
        this.dateLastMessage = new Timestamp(System.currentTimeMillis());

        this.status = ChatStatus.OPEN;

        this.isEvaluatedByAdvertiser = false;
        this.isEvaluatedByUser = false;
    }

    public Chat(User user, Announcement announcement) {
        this.dateOpen = new Timestamp(System.currentTimeMillis());
        this.dateLastMessage = new Timestamp(System.currentTimeMillis());

        this.status = ChatStatus.OPEN;

        this.user = user;
        this.advertiser = announcement.getAuthor();

        this.announcement = announcement;

        this.isEvaluatedByAdvertiser = false;
        this.isEvaluatedByUser = false;
    }

    public void close(){
        this.dateClose = new Timestamp(System.currentTimeMillis());
        this.dateLastMessage = new Timestamp(System.currentTimeMillis());

        this.status = ChatStatus.CLOSED;
    }

    public void delete(){
        this.dateDeleted = new Timestamp(System.currentTimeMillis());
        this.dateClose = new Timestamp(System.currentTimeMillis());

        this.status = ChatStatus.DELETED;
    }
}