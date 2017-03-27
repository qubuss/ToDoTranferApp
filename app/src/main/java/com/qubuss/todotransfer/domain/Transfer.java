package com.qubuss.todotransfer.domain;

/**
 * Created by qubuss on 01.03.2017.
 */

public class Transfer {
    private int id;
    private String name;
    private String bankName;

    public Transfer(int id, String name, String bankName) {
        this.id = id;
        this.name = name;
        this.bankName = bankName;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
