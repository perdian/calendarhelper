package de.perdian.apps.calendarhelper.services.google.calendar;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;

import java.util.List;

public interface CalendarClient {

    List<Calendar> loadCalendars(GoogleUser googleUser);

}
