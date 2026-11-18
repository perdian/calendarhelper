package de.perdian.apps.calendarbuddy.modules.jobs;

public interface JobProgressController {

    default void updateProgress(String progressMessage) {
        this.updateProgress(null, progressMessage);
    }

    default void updateProgress(Double progressValue) {
        this.updateProgress(progressValue, null);
    }

    void updateProgress(Double progressValue, String progressMessage);
    void cancel();

    boolean isCancelled();

}
