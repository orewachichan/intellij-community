package com.siyeh.igtest.style;

final public class MissortedModifiersInspection
{
    private native static int foo2();

    static private int m_bar = 4;
    static public int m_baz = 4;
    static final public int m_baz2 = 4;
    static final int m_baz3 = 4;

    static public void foo(){}

    static public class Foo
    {

    }

    public @Deprecated void foo3(){};


}
