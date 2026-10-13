package nick221122.crystalcooldown;

public final class CooldownState {
    private static long lockedUntilMillis;

    private CooldownState() {}

    public static void trigger() {
        lockedUntilMillis = System.currentTimeMillis() + CooldownConfig.getSeconds() * 1000L;
    }

    public static boolean isLocked() {
        return remainingMillis() > 0;
    }

    public static long remainingMillis() {
        return Math.max(0L, lockedUntilMillis - System.currentTimeMillis());
    }
}
