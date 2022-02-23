package applet;

import java.util.Stack;

public class Policy {
    short POLICY_MAX_SIZE = 256;
    SekripkInstructions[] policy = new SekripkInstructions[POLICY_MAX_SIZE];

    boolean eval(){
        Stack<Byte> stack = new Stack<>(); // wondering how good idea it is to implement this in JavaCard
        return false;
    }

}
