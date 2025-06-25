package com.liberis.habico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Building {

    private Long id;
    private String name;
    private String code;
    private boolean active;
    private CStatus status;
    private Address address;
    private List<Unit> units = new ArrayList<>();
}
