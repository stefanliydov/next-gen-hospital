package com.company.data.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaTestModel {
    private Long id;
    // Real name
    private String userName;
    private String userPhoneNumber;
    private String result;
}
