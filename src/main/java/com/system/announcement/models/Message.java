package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.announcement.auxiliary.enums.MessageStatus;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender", nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private MessageStatus status;

    private Timestamp date;

    public Message() {
        this.date = new Timestamp(System.currentTimeMillis());
        this.status = MessageStatus.SENT;
    }
}
