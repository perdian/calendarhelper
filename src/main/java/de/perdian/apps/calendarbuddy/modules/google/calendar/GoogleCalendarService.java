package de.perdian.apps.calendarbuddy.modules.google.calendar;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUser;

import java.util.List;

public interface GoogleCalendarService {

    List<GoogleCalendar> loadCalendars(GoogleUser googleUser);

    Event insertEvent(Event event, GoogleCalendar googleCalendar, GoogleUser googleUser);

}
