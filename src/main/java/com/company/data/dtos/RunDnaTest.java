package com.company.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunDnaTest {

   private String username;
   private String personalName;
   private String phoneNumber;
   private String email;
   private Integer age;
   private String dna;
   private String symptoms;
}
