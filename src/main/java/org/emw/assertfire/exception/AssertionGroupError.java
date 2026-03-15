package org.emw.assertfire.exception;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emw.assertfire.util.AnsiEscapeText;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * The error indicates error within GroupAssertion. It collects all errors thrown within the
 * GroupAssertion and throw them as a single error.
 */
public class AssertionGroupError extends AssertionError {
    private final List<Throwable> throwables;

    public AssertionGroupError(@NonNull String groupName, @NonNull List<Throwable> throwables) {
        super(buildMessage(groupName, throwables));

        this.throwables = throwables;
    }

    public AssertionGroupError(@NonNull List<Throwable> throwables) {
        this("", throwables);
    }

    public List<String> getMessages() {
        return throwables.stream().map(th -> th.getMessage() == null ? "" : th.getMessage()).toList();
    }

    public List<Throwable> throwables() {
        return Collections.unmodifiableList(throwables);
    }

    private static String buildMessage(@NonNull String groupName, @NonNull List<Throwable> errors) {
        final String text = AssertionGroupError.class.getCanonicalName() + ": " + errors.size() + (errors.size() > 1 ?  " errors in group" : " error in group");

        if (groupName.isEmpty()) {
            return text;
        } else {
            return text + ": '" + groupName + "'";
        }
    }

    /**
     * Override to provide messages that include a list of all errors that occurred within
     * GroupAssertion.
     * @return
     */
    @Override
    public String getMessage() {
        final StringBuilder messageBuilder = new StringBuilder(super.getMessage() == null ? "" : super.getMessage());
        int i = 0;

        for (Throwable error : throwables) {
            final String message = error.getMessage() == null ? "" : error.getMessage();

            messageBuilder.append("\n\n\t");
            messageBuilder.append(AnsiEscapeText.WhiteUnderlined.text("Error #" + (i + 1) + ":"));
            messageBuilder.append(" ").append(error.getClass().getCanonicalName()).append(": ");
            messageBuilder.append(AnsiEscapeText.WhiteBright.text(message.replaceAll("\n", "\n\t\t")));
            i++;
        }
        return messageBuilder.toString();
    }

    /**
     * Override to provide the stack trace of all errors that occurred within GroupAssertion.
     * @param s {@code PrintStream} to use for output
     */
    @Override
    public void printStackTrace(PrintStream s) {
        this.printStackTrace(new PrintWriter(s));
    }

    /**
     * Override to provide the stack trace of all errors that occurred within GroupAssertion.
     * @param s {@code PrintWriter} to use for output
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        final StringBuilder stackTraceBuilder = new StringBuilder();
        int i = 0;

        // Get list of errors and add to the string builder.
        for (Throwable error : throwables) {
            stackTraceBuilder.append("\n\t");
            stackTraceBuilder.append(AnsiEscapeText.WhiteUnderlined.text("Error Stack #" + (i + 1) + ":"));
            for (StackTraceElement stackTraceElement : error.getStackTrace()) {
                // Filter the stack trace to keep only the relevant output. No need to have the entire
                // list since the most of the lines would be duplicates.
                if (!stackTraceElement.toString().startsWith("java.") && !stackTraceElement.toString().startsWith("org.testng.") && !stackTraceElement.toString().contains(".gradle.")) {
                    stackTraceBuilder.append("\n\t\tat ");
                    stackTraceBuilder.append(stackTraceElement);
                }
            }
            stackTraceBuilder.append("\n");
            i++;
        }

        // Add the stack trace of this error.
        for (StackTraceElement stackTraceElement : this.getStackTrace()) {
            stackTraceBuilder.append("\n\tat ");
            stackTraceBuilder.append(stackTraceElement.toString());
        }

        // Print out the stack trace portion of the error.
        s.println(stackTraceBuilder);

        s.flush();
    }

}
