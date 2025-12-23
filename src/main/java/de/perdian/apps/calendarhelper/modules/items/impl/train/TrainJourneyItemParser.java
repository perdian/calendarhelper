package de.perdian.apps.calendarhelper.modules.items.impl.train;

import com.fasterxml.jackson.databind.JsonNode;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemParser;
import de.perdian.apps.calendarhelper.support.datetime.DateTimeHelper;

public class TrainJourneyItemParser implements ItemParser<TrainJourneyItem> {

    @Override
    public TrainJourneyItem parseItemDetails(JsonNode itemDetailsNode, ItemDefaults defaults) {
        TrainJourneyItem trainJourneyItem = new TrainJourneyItem(defaults);
        for (int i=0; i < itemDetailsNode.size(); i++) {
            TrainRideItem trainRideItem = trainJourneyItem.appendChildItem(defaults);
            this.mapTrainRideNode(itemDetailsNode.get(i), trainRideItem);
        }
        return trainJourneyItem;
    }

    private void mapTrainRideNode(JsonNode jsonNode, TrainRideItem trainRideItem) {
        trainRideItem.typeProperty().setValue(jsonNode.path("type").asText());
        trainRideItem.numberProperty().setValue(jsonNode.path("number").asText());
        trainRideItem.departureStationProperty().setValue(jsonNode.path("departureStation").asText());
        trainRideItem.getCalendarValues().startTimeProperty().setValue(DateTimeHelper.parseTime(jsonNode.path("departureTime").asText()));
        trainRideItem.departureTrackProperty().setValue(jsonNode.path("departureTrack").asText());
        trainRideItem.arrivalStationProperty().setValue(jsonNode.path("arrivalStation").asText());
        trainRideItem.getCalendarValues().endTimeProperty().setValue(DateTimeHelper.parseTime(jsonNode.path("arrivalTime").asText()));
        trainRideItem.arrivalTrackProperty().setValue(jsonNode.path("arrivalTrack").asText());
    }

}
