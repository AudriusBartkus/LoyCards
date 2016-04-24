package com.audbar.odre.loycards.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Audrius on 2016-04-22.
 */
public class LoyCard {
    public int id;
    public String cardNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date dateRegistered;
    public String cardType;
    public String description;
    public String imgUrl;
    public String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date birthDate;
    public String userId;
    public int cardTypeId;
    public String password;
}

