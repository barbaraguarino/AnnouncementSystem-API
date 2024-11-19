package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
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
@Entity
@Table(name = "TB_ANNOUNCEMENT")
public class Announcement implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_author", nullable = false)
    private User author;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city", nullable = false)
    private City city;

    private Timestamp date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_ANNOUNCEMENT_CATEGORY",
            joinColumns = @JoinColumn(name = "id_announcement"),
            inverseJoinColumns = @JoinColumn(name = "id_category",
                    nullable = false)
    )
    private Set<Category> categories = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
    private Set<Chat> chats = new HashSet<>();

    private float price;

    @Column(nullable = false)
    private AnnouncementStatus status;

    private Timestamp deletionDate;

    private String imageArchive;

    public Announcement(){
        this.date = new Timestamp(System.currentTimeMillis());
        this.deletionDate = null;
        this.status = AnnouncementStatus.VISIBLE;
    }

    public Announcement(UUID id,
                        String title,
                        String content,
                        User author,
                        City city,
                        Set<Category> categories,
                        float price,
                        String imageArchive) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.city = city;
        this.categories = categories;
        this.price = price;
        this.imageArchive = imageArchive;
        this.date = new Timestamp(System.currentTimeMillis());
        this.deletionDate = null;
        this.status = AnnouncementStatus.VISIBLE;
    }
}
