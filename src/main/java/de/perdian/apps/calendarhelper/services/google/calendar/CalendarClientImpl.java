package de.perdian.apps.calendarhelper.services.google.calendar;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
class CalendarClientImpl implements CalendarClient {

    private static final Logger log = LoggerFactory.getLogger(CalendarClientImpl.class);

    @Override
    public List<Calendar> loadCalendars(GoogleUser googleUser) {
        log.debug("Loading all calendars for Google user: {}", googleUser);
        return Collections.emptyList();
    }

}
