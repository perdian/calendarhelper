package de.perdian.apps.calendarbuddy.modules.jobs;

import javafx.beans.property.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Job {

    private final JobRunnable jobRunnable;
    private final String name;
    private final ObjectProperty<Double> progress;
    private final StringProperty progressMessage;
    private final BooleanProperty cancelled;

    Job(JobRunnable jobRunnable, String name) {
        this.jobRunnable = jobRunnable;
        this.name = name;
        this.progress = new SimpleObjectProperty<>(0d);
        this.progressMessage = new SimpleStringProperty(name);
        this.cancelled = new SimpleBooleanProperty(false);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("name", this.name)
                .append("progress", this.progress)
                .append("cancelled", this.cancelled)
                .toString();
    }

    JobRunnable getJobRunnable() {
        return this.jobRunnable;
    }

    String getName() {
        return this.name;
    }

    public Double getProgress() {
        return this.progress.get();
    }
    public ReadOnlyObjectProperty<Double> progressProperty() {
        return this.progress;
    }
    void setProgress(Double progress) {
        this.progress.set(progress);
    }

    public String getProgressMessage() {
        return this.progressMessage.get();
    }
    public ReadOnlyStringProperty progressMessageProperty() {
        return this.progressMessage;
    }
    void setProgressMessage(String progressMessage) {
        this.progressMessage.set(progressMessage);
    }

    public boolean isCancelled() {
        return this.cancelled.get();
    }
    public ReadOnlyBooleanProperty cancelledProperty() {
        return this.cancelled;
    }
    void setCancelled(boolean cancelled) {
        this.cancelled.set(cancelled);
    }

}
