package de.perdian.apps.calendarhelper.services.google.calendar;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;

import java.util.List;

public interface GoogleCalendarService {

    List<GoogleCalendar> loadCalendars(GoogleUser googleUser);

    Event insertEvent(Event event, GoogleCalendar googleCalendar, GoogleUser googleUser);

}
