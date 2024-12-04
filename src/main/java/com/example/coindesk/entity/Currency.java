package com.example.coindesk.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency {

    @Id
    private String code;
    private String chineseName;

    public Currency() {}

    public Currency(String code, String chineseName) {
        this.code = code;
        this.chineseName = chineseName;
    }

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getChineseName() { return chineseName; }
    public void setChineseName(String chineseName) { this.chineseName = chineseName; }
}
