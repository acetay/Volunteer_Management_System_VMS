package com.ntu.sctp.group1.DataTransferObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrolEditDto {

    private Integer id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private String timeOfProgram;

}
