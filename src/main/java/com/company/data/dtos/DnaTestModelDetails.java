package com.company.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaTestModelDetails  {
    private String userName;
    private String userUsername;
    private String userPhoneNumber;
    private String userEmail;
    private String result;
    private Timestamp creationDate;
    private Integer userAge;
    private String dna;
    private String symptoms;
}
