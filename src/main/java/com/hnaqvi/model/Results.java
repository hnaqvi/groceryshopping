package com.hnaqvi.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Results {

    private ArrayList<Result> results = new ArrayList<>();

    private BigDecimal total;

    public ArrayList<Result> getResults() {
        return results;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
