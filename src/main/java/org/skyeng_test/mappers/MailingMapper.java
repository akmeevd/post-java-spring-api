package org.skyeng_test.mappers;

import org.mapstruct.Mapper;
import org.skyeng_test.dto.MailingDto;
import org.skyeng_test.models.Mailing;

@Mapper(componentModel = "spring")
public interface MailingMapper {

    MailingDto fromMailingtoMailingDto(Mailing mailing);

}
