package info.preva1l.fadsb.multiserver;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    CREATE_ISLAND {
        @Override
        public void handle(JsonObject data) {

        }
    },
    LOAD_ISLAND {
        @Override
        public void handle(JsonObject data) {

        }
    },
    UNLOAD_ISLAND {
        @Override
        public void handle(JsonObject data) {

        }
    },
    DELETE_ISLAND {
        @Override
        public void handle(JsonObject data) {

        }
    };
    public abstract void handle(JsonObject data);
}