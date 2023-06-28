package com.yu.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairVO {
    private Integer id;
    private String detail;
    private Integer type;
    private Integer status;
    private String repairname;
    private LocalDateTime updatedate;
    private String name;
    private Integer dormitoryid;
    private Integer roomid;
}
