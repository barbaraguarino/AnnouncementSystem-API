package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "TB_MESSAGE")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private Timestamp date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender", nullable = false)
    private User sender;

    public Message() {
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public Message(Chat chat, User sender, String content) {
        this.chat = chat;
        this.sender = sender;

        this.content = content;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}