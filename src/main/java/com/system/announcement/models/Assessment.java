package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_ASSESSMENT")
public class Assessment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private float grade;

    @Column(nullable = false)
    private Timestamp date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evaluator_user", nullable = false)
    private User evaluatorUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rated_user", nullable = false)
    private User ratedUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    public Assessment(String title,
                      String description,
                      float grade,
                      User evaluatorUser,
                      User ratedUser,
                      Chat chat) {
        this.title = title;
        this.description = description;
        this.grade = grade;
        this.evaluatorUser = evaluatorUser;
        this.ratedUser = ratedUser;
        this.chat = chat;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}
