package org.charmeck.trailofhistory.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PointOfInterestTest {

  Gson gson;

  @Before public void setup() throws Exception {
    gson = new GsonBuilder().create();
  }

  @Test public void testGsonParsingList() throws Exception {
    List<PointOfInterest> pois =
        gson.fromJson(RESPONSE, (Type) new TypeToken<List<PointOfInterest>>() {
        }.getType());

    System.out.print("sdgsd");
  }

  public static final String RESPONSE = "[\n"
      + "  {\n"
      + "    \"name\": \"Julius Chambers\",\n"
      + "    \"id\": \"1233\",\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"Jane Wilkes\",\n"
      + "    \"id\": \"1233\",\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"William Henry Belk\",\n"
      + "    \"id\": \"1234\",\n"
      + "    \"latitude\": 35.212547,\n"
      + "    \"longitude\": -80.83627,\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"Thaddeus Tate\",\n"
      + "    \"id\": \"1235\",\n"
      + "    \"latitude\": 35.212177,\n"
      + "    \"longitude\": -80.836235,\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"Capt. Jack\",\n"
      + "    \"id\": \"1236\",\n"
      + "    \"latitude\": 35.21687,\n"
      + "    \"longitude\": -80.8327,\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"Chief And Settler\",\n"
      + "    \"id\": \"1237\",\n"
      + "    \"latitude\": 35.22006,\n"
      + "    \"longitude\": -80.83041,\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"Thomas Polk\",\n"
      + "    \"id\": \"1238\",\n"
      + "    \"latitude\": 35.21912,\n"
      + "    \"longitude\": -80.82952,\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  },\n"
      + "  {\n"
      + "    \"name\": \"James B Duke\",\n"
      + "    \"id\": \"1239\",\n"
      + "    \"description\": \"fdflkjdsf\",\n"
      + "    \"icon\": \"fgjlsfd\",\n"
      + "    \"photos\": [\n"
      + "      \"sdgs\",\n"
      + "      \"sdgsdg\"\n"
      + "    ]\n"
      + "  }\n"
      + "]";
}