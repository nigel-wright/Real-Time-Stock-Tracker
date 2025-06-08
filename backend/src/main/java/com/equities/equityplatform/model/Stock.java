package com.equities.equityplatform.model;

public class Stock {

    private String symbol;
    private String companyName;
    private String sector;
    private String industry;

    // Setters
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }
}
