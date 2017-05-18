package nl.example.application.business.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String text) throws Exception {
        if (text == null) {
            return null;
        }
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(formatter);
    }
}
