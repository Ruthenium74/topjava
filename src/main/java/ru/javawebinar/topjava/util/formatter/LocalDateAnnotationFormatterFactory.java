package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.Set;

public class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(LocalDate.class);
    }

    @Override
    public Printer<?> getPrinter(LocalDateFormat localDateFormat, Class<?> aClass) {
        return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateFormat localDateFormat, Class<?> aClass) {
        return new LocalDateFormatter();
    }
}
