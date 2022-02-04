package ru.draimdev.draimmarriage.Utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    private static final String RAW_GRADIENT_HEX_REGEX = "<\\$#[A-Fa-f0-9]{6}>";
    public static final String HEX_REGEX = "#[A-Fa-f0-9]{6}";
    public static final String TAG_REGEX = "^[A-Za-z0-9_]{1,16}$";
    public static final String RAW_HEX_REGEX = "<#[A-Fa-f0-9]{6}>";

    public static String colorMessage(String legacyMSG) {
        try{
            if(legacyMSG.isEmpty()){
                return legacyMSG;
            }
            if (ChatColor.translateAlternateColorCodes('&', legacyMSG).isEmpty()){
                return ChatColor.translateAlternateColorCodes('&', legacyMSG);
            }
        } catch (NullPointerException e){
            return legacyMSG;
        }
    }
}
