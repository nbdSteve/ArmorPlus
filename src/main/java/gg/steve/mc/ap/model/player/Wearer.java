package gg.steve.mc.ap.model.player;

import lombok.Value;

import java.util.UUID;

@Value
public class Wearer {
    UUID playerId;
    String setName;
}
