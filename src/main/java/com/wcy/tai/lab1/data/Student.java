package com.wcy.tai.lab1.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    private String surname;
}
