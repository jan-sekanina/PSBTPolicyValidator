package applet;

public class Stack {
    short STACK_SIZE = 256;
    private Byte[] stack;
    private short current_pointer;

    public Stack(){
        stack = new Byte[256];
        current_pointer = 0;
    }

    boolean isEmpty(){
        return current_pointer <= 0;
    }

    void push(byte i){
        assert current_pointer < STACK_SIZE;
        stack[current_pointer] = i;
        current_pointer++;
    }

    byte pop(){
        assert !this.isEmpty();
        current_pointer--;
        return stack[current_pointer + 1];
    }

    byte top(){
        return stack[current_pointer - 1];
    }
}
