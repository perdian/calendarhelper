package de.perdian.apps.calendarhelper.services.google.calendar;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;

import java.util.List;

public interface GoogleCalendarService {

    List<GoogleCalendar> loadCalendars(GoogleUser googleUser);

}
