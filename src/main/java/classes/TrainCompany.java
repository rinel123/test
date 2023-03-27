import java.io.FileReader;
import java.io.FileWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TrainCompany {

    private static final double ZONE_1_TO_2_PRICE = 2.4;
    private static final double ZONE_3_TO_4_PRICE = 2.0;
    private static final double ZONE_3_TO_1_2_PRICE = 2.8;
    private static final double ZONE_4_TO_1_2_PRICE = 3.0;
    private static final double ZONE_1_2_TO_3_PRICE = 2.8;
    private static final double ZONE_1_2_TO_4_PRICE = 3.0;

    private static final int ZONE_1_RADIUS = 5;
    private static final int ZONE_2_RADIUS = 15;
    private static final int ZONE_3_RADIUS = 25;
    private static final int ZONE_4_RADIUS = 35;

    private static final Map<String, Integer> STATION_TO_ZONE_MAP = new HashMap<String, Integer>();
    static {
        STATION_TO_ZONE_MAP.put("A", 1);
        STATION_TO_ZONE_MAP.put("B", 1);
        STATION_TO_ZONE_MAP.put("C", 2);
        STATION_TO_ZONE_MAP.put("D", 2);
        STATION_TO_ZONE_MAP.put("E", 2);
        STATION_TO_ZONE_MAP.put("F", 3);
        STATION_TO_ZONE_MAP.put("G", 4);
        STATION_TO_ZONE_MAP.put("H", 4);
        STATION_TO_ZONE_MAP.put("I", 4);
    }

    private static int getZone(int x, int y) {
        int distanceFromCentre = (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        if (distanceFromCentre <= ZONE_1_RADIUS) {
            return 1;
        } else if (distanceFromCentre <= ZONE_2_RADIUS) {
            return 2;
        } else if (distanceFromCentre <= ZONE_3_RADIUS) {
            return 3;
        } else {
            return 4;
        }
    }

    private static double getPrice(int zoneFrom, int zoneTo) {
        if (zoneFrom == zoneTo) {
            return 0;
        } else if (zoneFrom == 1 && zoneTo == 2 || zoneFrom == 2 && zoneTo == 1) {
            return ZONE_1_TO_2_PRICE;
        } else if (zoneFrom == 3 && (zoneTo == 1 || zoneTo == 2) || zoneFrom == 1 && zoneTo == 3 || zoneFrom == 2 && zoneTo == 3) {
            return ZONE_3_TO_1_2_PRICE;
        } else if (zoneFrom == 4 && (zoneTo == 1 || zoneTo == 2) || zoneFrom == 1 && zoneTo == 4 || zoneFrom == 2 && zoneTo == 4) {
            return ZONE_4_TO_1_2_PRICE;
        } else if (zoneFrom == 1 || zoneFrom == 2 && zoneTo == 3 || zoneFrom == 3 && (zoneTo == 1 || zoneTo == 2)) {
            return ZONE_1_2_TO_3_PRICE;
        } else {
            return ZONE_1_2_TO_4_PRICE;
        }
    }

    public static void main(String[] args) throws Exception {
        FileReader reader = new FileReader("taps.json");
        JSONTokener tokener = new JSONTokener(reader);
        JSONArray trainArray = new JSONArray(tokener);

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < trainArray.length(); i++) {
            JSONObject ticketObject = trainArray.getJSONObject(i);

            String passengerName = ticketObject.getString("passenger");
            int[] fromCoordinates = {ticketObject.getInt("from_x"), ticketObject.getInt("from_y")};
            int[] toCoordinates = {ticketObject.getInt("to_x"), ticketObject.getInt("to_y")};
            LocalDateTime departureTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(ticketObject.getLong("departure_time")), ZoneOffset.UTC);

            int fromZone = getZone(fromCoordinates[0], fromCoordinates[1]);
            int toZone = getZone(toCoordinates[0], toCoordinates[1]);
            double price = getPrice(fromZone, toZone);

            Ticket ticket = new Ticket(passengerName, fromZone, toZone, price, departureTime);
            tickets.add(ticket);
        }

        FileWriter writer = new FileWriter("customerSummaries.json");
        JSONArray ticketArray = new JSONArray(tickets);
        writer.write(ticketArray.toString());
        writer.close();
    }
}
