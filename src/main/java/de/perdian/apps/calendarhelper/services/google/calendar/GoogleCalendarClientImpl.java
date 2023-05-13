package de.perdian.apps.calendarhelper.services.google.calendar;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
class GoogleCalendarClientImpl implements GoogleCalendarClient {

    private static final Logger log = LoggerFactory.getLogger(GoogleCalendarClientImpl.class);

    @Override
    public List<GoogleCalendar> loadCalendars(GoogleUser googleUser) {
        log.debug("Loading all calendars for Google user: {}", googleUser);
        return Collections.emptyList();
    }

}
