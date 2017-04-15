package nami.connector.json;

import com.github.tobiasmiosczka.nami.extendetjnami.namitypes.Baustein;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nami.connector.namitypes.enums.*;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonHelp {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .registerTypeAdapter(Beitragsart.class, new BeitragsartDeserializer())
            .registerTypeAdapter(Geschlecht.class, new GeschlechtDeserializer())
            .registerTypeAdapter(MitgliedStatus.class, new MitgliedStatusDeserializer())
            .registerTypeAdapter(Mitgliedstyp.class, new MitgliedStatusDeserializer())
            .registerTypeAdapter(Stufe.class, new StufeDeserializer())
            .registerTypeAdapter(Baustein.class, new BausteinDeserializer())
            .create();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}