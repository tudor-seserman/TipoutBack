package com.tipout.Tipout.models;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity{

    private String name;
}
