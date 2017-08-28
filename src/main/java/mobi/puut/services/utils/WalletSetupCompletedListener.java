package mobi.puut.services.utils;

import org.bitcoinj.wallet.Wallet;

/**
 * Created by Chaklader on 6/24/17.
 */
public interface WalletSetupCompletedListener {
    void onSetupCompleted(Wallet wallet);
}