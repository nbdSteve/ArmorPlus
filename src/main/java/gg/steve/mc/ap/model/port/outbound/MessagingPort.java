package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.effect.NotificationSound;
import gg.steve.mc.ap.model.id.PlayerId;

/**
 * Outbound port for sending messages, sounds, and dispatching commands.
 * The domain calls these for set notifications (equip/unequip messages,
 * sounds, triggered commands). Adapters implement via the platform
 * messaging API. Implementations live outside the model package.
 */
public interface MessagingPort {

    void sendMessage(PlayerId playerId, String message);

    void playSound(PlayerId playerId, NotificationSound sound);

    void dispatchCommand(String command);
}
