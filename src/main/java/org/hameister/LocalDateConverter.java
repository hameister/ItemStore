package org.hameister;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Joern Hameister on 24.01.16.
 */

    @Converter(autoApply = true)
    public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

        @Override
        public Date convertToDatabaseColumn(LocalDate attribute) {
            return attribute == null ? null : Date.valueOf(attribute);
        }

        @Override
        public LocalDate convertToEntityAttribute(Date dbData) {
            return dbData == null ? null : dbData.toLocalDate();
        }
    }

