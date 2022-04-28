package applet;

import java.util.Stack;

public class Policy {
    short POLICY_MAX_SIZE = 256;
    short[] policy = new short[POLICY_MAX_SIZE];

    boolean eval(){
        return false;
    }

}
