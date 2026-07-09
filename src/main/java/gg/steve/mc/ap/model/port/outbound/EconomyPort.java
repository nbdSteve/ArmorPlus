package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.id.PlayerId;

/**
 * Outbound port for economy operations (balance checks, withdrawals).
 * The domain calls these during purchase flows to verify and deduct funds.
 * Adapters implement via Vault or other economy plugins.
 * Implementations live outside the model package.
 */
public interface EconomyPort {

    double getBalance(PlayerId playerId);

    boolean withdraw(PlayerId playerId, double amount);

    boolean isAvailable();
}
