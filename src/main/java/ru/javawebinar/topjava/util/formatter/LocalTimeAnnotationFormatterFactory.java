package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.Set;

public class LocalTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(LocalTime.class);
    }

    @Override
    public Printer<?> getPrinter(LocalTimeFormat localTimeFormat, Class<?> aClass) {
        return new LocalTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalTimeFormat localTimeFormat, Class<?> aClass) {
        return new LocalTimeFormatter();
    }
}
