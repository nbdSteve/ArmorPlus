package gg.steve.mc.ap.model.notification;

import gg.steve.mc.ap.model.effect.NotificationSoundSpec;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ArmorSetNotification {
    List<String> messages;
    NotificationSoundSpec sound;
    List<String> commands;
}
