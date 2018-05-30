package com.example.minjena.myapplication.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerChat {
    private String ridx;
    private String from_idx;
    private Date reg_date;
    private String msg;

    public RecyclerChat(String ridx, String from_idx, String msg)
    {
        this.ridx=ridx;
        this.from_idx=from_idx;
        //this.reg_date=reg_date;
        this.msg=msg;
    }

    public String getRidx()
    {
        return ridx;
    }
    public String getFrom_idx()
    {
        return from_idx;
    }
    public Date getReg_date()
    {
        return reg_date;
    }
    public String getMsg()
    {
        return msg;
    }

}
