package gg.steve.mc.ap.model.notification;

import gg.steve.mc.ap.model.effect.SoundSpec;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Notification {
    List<String> messages;
    SoundSpec sound;
    List<String> commands;
}
