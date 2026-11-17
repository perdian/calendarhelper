package de.perdian.apps.calendarbuddy.modules.jobs;

import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class JobExecutor {

    private static final Logger log = LoggerFactory.getLogger(JobExecutor.class);

    private final ObjectProperty<Job> currentJob;
    private final BlockingQueue<Job> pendingJobs;
    private final BooleanProperty running;

    JobExecutor() {
        this.currentJob = new SimpleObjectProperty<>();
        this.pendingJobs = new LinkedBlockingQueue<>();
        this.running = new SimpleBooleanProperty(false);
        this.running.bind(this.currentJob.isNotNull());
    }

    @PostConstruct
    void initialize() {
        Thread.ofPlatform()
            .name(this.getClass().getSimpleName())
            .daemon(true)
            .start(() -> this.processPendingJobs());
    }

    private void processPendingJobs() {
        try {
            while (true) {
                Job nextJob = this.getPendingJobs().take();
                this.processJob(nextJob);
            }
        } catch (InterruptedException e) {
            log.warn("JobExecutor thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void processJob(Job job) {
        log.debug("Processing job: {}", job);
        Platform.runLater(() -> this.setCurrentJob(job));
        try {
            JobProgressController jobProgressController = new JobProgressController() {
                @Override public void updateProgress(Double progress, String progressMessage) {
                    Platform.runLater(() -> {
                        job.setProgress(progress);
                        job.setProgressMessage(progressMessage);
                    });
                }
                @Override public void cancel() {
                    job.setCancelled(true);
                }
                @Override public boolean isCancelled() {
                    return job.isCancelled();
                }
            };
            job.getJobRunnable().run(jobProgressController);
            if (!job.isCancelled()) {
                jobProgressController.updateProgress(1d);
            }
        } finally {
            Platform.runLater(() -> this.setCurrentJob(null));
        }
    }

    public Job submitJob(JobRunnable jobRunnable) {
        return this.submitJob(jobRunnable, null);
    }

    public Job submitJob(JobRunnable jobRunnable, String jobName) {
        Job job = new Job(jobRunnable, jobName);
        this.getPendingJobs().add(job);
        return job;
    }

    public Job getCurrentJob() {
        return this.currentJob.get();
    }
    public ReadOnlyObjectProperty<Job> currentJobProperty() {
        return this.currentJob;
    }
    private void setCurrentJob(Job currentJob) {
        this.currentJob.set(currentJob);
    }

    private BlockingQueue<Job> getPendingJobs() {
        return this.pendingJobs;
    }

    public boolean isRunning() {
        return this.running.get();
    }
    public ReadOnlyBooleanProperty runningProperty() {
        return this.running;
    }

}
