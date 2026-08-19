package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.AddressType;

public record AddressResponse(
        Long id,
        AddressType addressType,
        String addLine1,
        String addLine2,
        String city,
        String state,
        String postalCode,
        String country
) {}
