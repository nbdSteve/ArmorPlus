package gg.steve.mc.ap.model.player;

import gg.steve.mc.ap.model.id.ArmorSetId;
import gg.steve.mc.ap.model.id.PlayerId;
import lombok.Value;

@Value
public class ArmorSetWearer {
    PlayerId playerId;
    ArmorSetId setId;
}
