package com.poly.coffee.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchCriteria {

    // key: name, email, price,...
    private String key;

    // operation: :, >, <
    private String operation;

    // value: Nguyen Van A, 01/01/2000, 59.000
    private Object value;
}
