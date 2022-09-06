package gay.gaycraft.plugin.permission;

import gay.gaycraft.plugin.wrapper.PlayerWrapper;

public enum Permission {
    KICK,
    BAN,
    MUTE,
    FREEZE,
    RANK;

    public static boolean has(PlayerWrapper player, Permission permission) {
        switch (player.getGroup()) {
            case SUPERADMIN:
            case ADMIN:
                return true;
            case MOD:
                if (permission == KICK ||
                        permission == MUTE ||
                        permission == FREEZE)
                    return true;
            default:
                break;
        }

        return false;
    }

}
