package gg.steve.mc.ap.model.player;

import lombok.Value;

import java.util.UUID;

@Value
public class ArmorSetWearer {
    UUID playerId;
    String setName;
}
