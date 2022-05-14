package com.iem.iemserver.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
public class DateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    private Integer departmentId;
    private Integer empHistoryId;

}
