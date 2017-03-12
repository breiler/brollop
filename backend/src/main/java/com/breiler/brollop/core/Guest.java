package com.breiler.brollop.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jongo.marshall.jackson.oid.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Guest {
    @MongoId
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String notes;
}
