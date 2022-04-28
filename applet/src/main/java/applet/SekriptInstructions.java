package applet;

public class SekriptInstructions {
    private final short VERSION = 1;
    public static short TRUE = 128;
    public static short FALSE = 129;
    public static short AND = 130;
    public static short OR = 131;
    public static short IF = 132;
    public static short THEN = 133;
    public static short ELSE = 134;
    public static short GREATER_THAN = 135;
    public static short TIME_SIGNATURE_CHECK  = 136;

    short skw_and(short first, short second){
        assert first == TRUE || first == FALSE;
        assert second == TRUE || second == FALSE;

        if (first == TRUE && second == TRUE){
            return TRUE;
        }
        return FALSE;
    }

    short skw_or(short first, short second){
        assert first == TRUE || first == FALSE;
        assert second == TRUE || second == FALSE;

        if (first == TRUE || second == TRUE){
            return TRUE;
        }
        return FALSE;
    }

    short skw_time_signature_check(short publicKeyStorage, short checkWith){
        return FALSE;
    }
}
