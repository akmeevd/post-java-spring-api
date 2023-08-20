package org.skyeng_test.dto;

import lombok.Data;
import org.skyeng_test.models.Recipient;
import org.skyeng_test.models.Routing;
import org.skyeng_test.models.Status;
import org.skyeng_test.models.Type;
@Data
public class MailingDto {

    private Type type;
    private Recipient recipient;
    private Status status;
}
