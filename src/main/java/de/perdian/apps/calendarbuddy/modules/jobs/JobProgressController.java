package de.perdian.apps.calendarbuddy.modules.jobs;

public interface JobProgressController {

    default void updateProgress(String message) {
        this.updateProgress(null, message);
    }

    default void updateProgress(Double progress) {
        this.updateProgress(progress, null);
    }

    void updateProgress(Double progress, String message);
    void cancel();

    boolean isCancelled();

}
