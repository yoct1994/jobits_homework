package com.example.jabits_homework.entity.crud;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CRUD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long listId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
