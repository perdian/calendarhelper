package de.perdian.apps.calendarbuddy.modules.jobs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Job {

    private final JobRunnable jobRunnable;
    private final String name;
    private final BooleanProperty cancelled;

    Job(JobRunnable jobRunnable, String name) {
        this.jobRunnable = jobRunnable;
        this.name = name;
        this.cancelled = new SimpleBooleanProperty(false);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("name", this.name)
                .append("cancelled", this.cancelled)
                .toString();
    }

    JobRunnable getJobRunnable() {
        return this.jobRunnable;
    }

    String getName() {
        return this.name;
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
