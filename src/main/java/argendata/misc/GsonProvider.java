package argendata.misc;


import java.lang.reflect.Type;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public enum GsonProvider {
	INSTANCE;

	private final class TimestampJsonDeserializer implements
	JsonDeserializer<Timestamp>, JsonSerializer<Timestamp> {

		public Timestamp deserialize(JsonElement arg0, Type arg1,
				JsonDeserializationContext arg2) throws JsonParseException {
			return new Timestamp(arg0.getAsLong());
		}

		public JsonElement serialize(Timestamp arg0, Type arg1,
				JsonSerializationContext arg2) {
			return new JsonPrimitive(arg0.getTime());
		}
		
	}
	
	private Gson gson = null;

	private GsonProvider() {
		gson = new GsonBuilder()
				 .setPrettyPrinting()
				.serializeSpecialFloatingPointValues()
				.registerTypeAdapter(Timestamp.class, 
						new TimestampJsonDeserializer())
				.create();
	}

	public static Gson getGson() {
		return INSTANCE.gson;
	}
}