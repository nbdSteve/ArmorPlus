package com.nbdsteve.carmor.method.gui;

import com.nbdsteve.carmor.file.LoadCarmorFiles;

/**
 * Class to get the price of a piece or armor
 */
public class GetPiecePrice {

    /**
     * Return the price of the armor piece as an integer
     *
     * @param setNumber the armor set
     * @param itemType  the type or item, i.e. helmet
     * @param lcf       LoadCarmorFiles instance
     * @return
     */
    public static int getPiecePrice(String setNumber, String itemType, LoadCarmorFiles lcf) {
        String[] item = null;
        try {
            item = itemType.split("_");
        } catch (Exception e) {
            System.out.println("Please use a piece of armor!");
        }
        if (item != null) {
            String[] piecePrice = lcf.getArmorGui().getString(setNumber + "." + item[1]).split(":");
            return Integer.parseInt(piecePrice[2]);
        }
        return 0;
    }
}
