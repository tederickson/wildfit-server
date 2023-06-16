package com.wildfit.server.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECIPE", indexes = {@Index(name = "season_idx", columnList = "season")})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String introduction;
    private String name;
    private String season;

    private int prepTimeMin;
    private int cookTimeMin;
    private String servingUnit;
    private int servingQty;

    private String instructions;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
