package org.skyeng_test.dto;


import lombok.Data;
import org.skyeng_test.models.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class MailingRegDto {

    @Enumerated(EnumType.STRING)
    private Type type;
    private int postIndexFrom;
    private int interPostIndex;
    private int postIndexTo;
    private String recipientName;
    private String recipientAddress;
    private int recipientIndex;
}
