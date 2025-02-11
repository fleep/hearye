package org.fleep.hearye.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_type")
public class NotificationType {
    public static final int TYPE_EMAIL = 1;
    public static final int TYPE_CRM = 2;
    public static final int TYPE_CHAT = 3;

    public NotificationType() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    public NotificationType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
