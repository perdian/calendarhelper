package de.perdian.apps.calendarhelper.support.google.calendar;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.auth.http.HttpCredentialsAdapter;
import de.perdian.apps.calendarhelper.support.google.GoogleApiException;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private static final Logger log = LoggerFactory.getLogger(GoogleCalendarServiceImpl.class);

    private NetHttpTransport httpTransport = null;
    private JsonFactory jsonFactory = null;

    GoogleCalendarServiceImpl() {
        try {
            this.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
            this.setJsonFactory(GsonFactory.getDefaultInstance());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create Google API client classes", e);
        }
    }

    @Override
    public List<GoogleCalendar> loadCalendars(GoogleUser googleUser) {

        log.trace("Loading all calendars for Google user: {}", googleUser);
        Calendar calendarService = this.createCalendarService(googleUser);
        try {

            List<GoogleCalendar> googleCalendars = new ArrayList<>();
            String pageToken = null;
            do {

                CalendarList calendarList = calendarService.calendarList().list()
                        .setPageToken(pageToken)
                        .setMinAccessRole("writer")
                        .execute();

                for (CalendarListEntry calendarListEntry : calendarList.getItems()) {
                    googleCalendars.add(new GoogleCalendar(calendarListEntry));
                }
                pageToken = calendarList.getNextPageToken();

            } while (pageToken != null);

            log.debug("Loaded {} calendars for Google user: {}", googleCalendars.size(), googleUser);
            return googleCalendars;

        } catch (Exception e) {
            throw new GoogleApiException("Cannot load Google calendars", e);
        }

    }

    @Override
    public Event insertEvent(Event event, GoogleCalendar googleCalendar, GoogleUser googleUser) {
        log.trace("Create Google calendar event [{}] for user: {}", event, googleUser);
        Calendar calendarService = this.createCalendarService(googleUser);
        try {

            // Check if we already have an event for the specified identifier.
            // If yes then we need to perform an UPDATE instead of an INSERT
            Event existingEvent = this.findExistingEventById(event.getId(), googleCalendar, googleUser);
            if (existingEvent == null) {
                return calendarService.events()
                    .insert(googleCalendar.getId(), event)
                    .setSendUpdates("all")
                    .execute();
            } else {
                return calendarService.events()
                    .update(googleCalendar.getId(), event.getId(), event)
                    .setSendUpdates("all")
                    .execute();
            }

        } catch (Exception e) {
            throw new GoogleApiException("Cannot create Google calendar event", e);
        }
    }

    private Event findExistingEventById(String eventId, GoogleCalendar googleCalendar, GoogleUser googleUser) throws IOException {
        if (StringUtils.isEmpty(eventId)) {
            return null;
        } else {
            try {
                Calendar calendarService = this.createCalendarService(googleUser);
                return calendarService.events().get(googleCalendar.getId(), eventId).execute();
            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 404) {
                    return null;
                } else {
                    throw e;
                }
            }
        }
    }

    private Calendar createCalendarService(GoogleUser googleUser) {
        return new Calendar.Builder(this.getHttpTransport(), this.getJsonFactory(), new HttpCredentialsAdapter(googleUser.getCredentials()))
                .setApplicationName("Calendar Helper")
                .build();
    }

    private NetHttpTransport getHttpTransport() {
        return this.httpTransport;
    }
    private void setHttpTransport(NetHttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    private JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    private void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

}
