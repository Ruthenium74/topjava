package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.exception.ErrorInfo;

public class ExceptionTestData {
    private ExceptionTestData() {
    }

    public static final String CONTEXT_PATH = "http://localhost";
    public static TestMatcher<ErrorInfo> ERROR_INFO_MATCHER = TestMatcher.usingEqualsAssertions(ErrorInfo.class);
    public static TestMatcher<ErrorInfo> ERROR_INFO_MATCHER_IGNORING_MESSAGE =
            TestMatcher.usingFieldsWithIgnoringAssertions(ErrorInfo.class, "details");
}
