package applet;


// alternative might be using constants like in class AppletInstructions. Might make more readable code.
public enum SekripkInstructions {
    TRUE((short) 128),
    FALSE((short) 129),
    AND((short) 130),
    OR((short) 131),
    IF((short) 132),
    THEN((short) 133),
    ELSE((short) 134),
    GREATER_THAN((short) 135),
    TIME_SIGNATURE_CHECK((short) 136);

    private short value;
    SekripkInstructions(short i) {
        this.value = i;
    }

    SekripkInstructions skw_and(SekripkInstructions first, SekripkInstructions second){
        assert first == TRUE || first == FALSE;
        assert second == TRUE || second == FALSE;

        if (first == TRUE && second == TRUE){
            return TRUE;
        }
        return FALSE;
    }

    SekripkInstructions skw_or(SekripkInstructions first, SekripkInstructions second){
        assert first == TRUE || first == FALSE;
        assert second == TRUE || second == FALSE;

        if (first == TRUE || second == TRUE){
            return TRUE;
        }
        return FALSE;
    }

    SekripkInstructions skw_time_signature_check(SekripkInstructions publicKeyStorage, SekripkInstructions checkWith){
        return FALSE;
    }
}
