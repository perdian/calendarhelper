package de.perdian.apps.calendarbuddy.modules.execution;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarModel;
import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarService;
import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUserModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExecutionModel {

    private final BooleanProperty available;
    private final BooleanProperty busy;
    private final GoogleCalendarModel googleCalendarModel;
    private final GoogleUserModel googleUserModel;
    private final ItemListModel itemListModel;
    private final DefaultsModel defaultsModel;
    private final JobExecutor jobExecutor;
    private final GoogleCalendarService googleCalendarService;

    ExecutionModel(GoogleCalendarModel googleCalendarModel, GoogleUserModel googleUserModel, ItemListModel itemListModel, DefaultsModel defaultsModel, JobExecutor jobExecutor, GoogleCalendarService googleCalendarService) {
        this.available = new SimpleBooleanProperty();
        this.available.bind(googleCalendarModel.selectedCalendarProperty().isNotNull().and(Bindings.isNotEmpty(itemListModel.getItems())));
        this.busy = new SimpleBooleanProperty();
        this.busy.bind(jobExecutor.runningProperty());
        this.googleCalendarModel = googleCalendarModel;
        this.googleUserModel = googleUserModel;
        this.itemListModel = itemListModel;
        this.defaultsModel = defaultsModel;
        this.jobExecutor = jobExecutor;
        this.googleCalendarService = googleCalendarService;
    }

    public void execute() {
        this.getJobExecutor().submitJob(progress -> {
            progress.updateProgress(-1d, "Creating Calendar Items");
            List<Event> eventObjects = this.getItemListModel().getItems().stream()
                .flatMap(item -> item.createEvents(this.getDefaultsModel()).stream())
                .toList();
            for (int i=0; i < eventObjects.size() && !progress.isCancelled(); i++) {
                Event eventObject = eventObjects.get(i);
                double progressValue = (double)(i+1) / (eventObjects.size() + 1);
                progress.updateProgress(progressValue, "Creating Calendar Item:  " + eventObject.getSummary());
                this.getGoogleCalendarService().insertEvent(eventObject, this.getGoogleCalendarModel().getSelectedCalendar(), this.getGoogleUserModel().getCurrentUser());
            }
        });
    }

    public boolean isAvailable() {
        return available.get();
    }
    public ReadOnlyBooleanProperty availableProperty() {
        return this.available;
    }

    public boolean isBusy() {
        return this.busy.get();
    }
    public ReadOnlyBooleanProperty busyProperty() {
        return busy;
    }

    private GoogleCalendarModel getGoogleCalendarModel() {
        return this.googleCalendarModel;
    }

    private GoogleUserModel getGoogleUserModel() {
        return this.googleUserModel;
    }

    private ItemListModel getItemListModel() {
        return this.itemListModel;
    }

    private DefaultsModel getDefaultsModel() {
        return this.defaultsModel;
    }

    private JobExecutor getJobExecutor() {
        return this.jobExecutor;
    }

    private GoogleCalendarService getGoogleCalendarService() {
        return this.googleCalendarService;
    }

}
