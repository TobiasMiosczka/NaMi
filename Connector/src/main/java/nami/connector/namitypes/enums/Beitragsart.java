package nami.connector.namitypes.enums;

/**
 * Beschreibt die möglichen Beitragsarten. Ein möglicher Stiftungseuro wird
 * ignoriert, weil er für die lokalen Anwendungen keine Rolle spielt.
 * 
 * @author Fabian Lipp
 * 
 */
public enum Beitragsart {

    //Normaler Beitragssatz
    VOLLER_BEITRAG("Voller Beitrag"),

    //Familienermäßigung
    FAMILIEN_BEITRAG("Familienermäßigt"),

    //Sozialermäßigung
    SOZIALERMAESSIGUNG("Sozialermäßigt"),

    //Normaler Beitragssatz
    VOLLER_BEITRAG_STIFTUNGSEURO("Voller Beitrag - Stiftungseuro"),

    //Familienermäßigung
    FAMILIEN_BEITRAG_STIFTUNGSEURO("Familienermäßigt - Stiftungseuro"),

    //Sozialermäßigung
    SOZIALERMAESSIGUNG_STIFTUNGSEURO("Sozialermäßigt - Stiftungseuro"),

    //Personen, die keinen Mitgliedsbeitrag bezahlen müssen
    KEIN_BEITRAG("(keine Beitragsarten zugeordnet)");

    private String tag;

    Beitragsart(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    /**
     * Setzt einen String in die entsprechende Beitragsart um.
     * 
     * @param str
     *            String-Repräsentation der Beitragsart
     * @return entsprechende Beitragsart; <code>null</code>, wenn keine
     *         Beitragsart angegeben ist
     */
    public static Beitragsart fromString(String str) {
        if(str == null || str.equals("")) {
            return KEIN_BEITRAG;
        }
        for(Beitragsart beitragsart : Beitragsart.values()) {
            if (beitragsart.getTag().equals(str)) {
                return beitragsart;
            }
        }
        throw new IllegalArgumentException("Unexpected String for Beitragsart:" + str);
    }
}
