package gay.gaycraft.plugin.permission;

import net.md_5.bungee.api.ChatColor;

public enum Group {
    
    MEMBER("Member", ""),
    TRUSTED("Trusted", ChatColor.AQUA + "" + ChatColor.BOLD + "T "),
    MOD("Moderator", ChatColor.BLUE + "" + ChatColor.BOLD + "M "),
    ADMIN("Admin", ChatColor.RED + "" + ChatColor.BOLD + "A "),
    SUPERADMIN("Super Admin", ChatColor.DARK_RED + "" + ChatColor.BOLD + "S ");

    private final String displayname, prefix;

    private Group(String displayname, String prefix) {
        this.displayname = displayname;
        this.prefix = prefix;
    }

    public String getDisplayName() {
        return displayname;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean inherits(Group group) {
        switch (this) {
            case SUPERADMIN:
                return true;
            case ADMIN:
                if (group == ADMIN)
                    return true;
            case MOD:
                if (group == MOD)
                    return true;
            case TRUSTED:
                if (group == TRUSTED)
                    return true;
            case MEMBER:
                if (group == MEMBER)
                    return true;
        }

        return false;
    }

}
