package gg.steve.mc.ap.model.effect;

import gg.steve.mc.ap.model.id.SoundId;
import lombok.Value;

@Value
public class NotificationSound {
    SoundId name;
    float volume;
    float pitch;
}
