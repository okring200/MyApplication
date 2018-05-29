package com.example.minjena.myapplication.model;

public class RecyclerRoom {
    private String rname;
    private String ridx;
    public RecyclerRoom(String ridx, String rname)
    {
        this.rname = rname;
        this.ridx = ridx;
    }

    public String getName()
    {
        return rname;
    }
    public String getRidx() { return ridx; }
}
