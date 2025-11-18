package com.ss.address;


import jakarta.persistence.Embeddable;
import lombok.Data;
@Data
@Embeddable
public class Address {
    private String city;
    private String state;
    private String pincode;
}
