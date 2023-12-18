package com.tipout.Tipout.models;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role{
    @Id
    @GeneratedValue
    private long id;
    private String name;
}
