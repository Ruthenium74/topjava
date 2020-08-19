package ru.javawebinar.topjava.util.exception;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final List<String> details;

    @ConstructorProperties({"url", "type", "details"})
    public ErrorInfo(CharSequence url, ErrorType type, List<String> details) {
        this.url = url.toString();
        this.type = type;
        this.details = new ArrayList<>(details);
    }

    public ErrorInfo(CharSequence url, ErrorType type, String... detail) {
        this.url = url.toString();
        this.type = type;
        this.details = List.of(detail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorInfo errorInfo = (ErrorInfo) o;
        return Objects.equals(url, errorInfo.url) &&
                type == errorInfo.type &&
                Objects.equals(details, errorInfo.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, type, details);
    }
}