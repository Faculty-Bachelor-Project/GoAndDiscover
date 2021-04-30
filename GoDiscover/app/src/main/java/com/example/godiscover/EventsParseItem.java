package com.example.godiscover;

public class EventsParseItem {
    private String img_url;
    private String img_width;
    private String img_height;
    private String title;
    private String location;
    private String text_date;
    private String web_url;

    public EventsParseItem(){}

    public EventsParseItem(String img_url, String img_width, String img_height, String title, String location, String text_date,String web_url)
    {
        this.img_url = img_url;
        this.img_width = img_width;
        this.img_height = img_height;
        this.title = title;
        this.location = location;
        this.text_date = text_date;
        this.web_url = web_url;
    }

    public String getImg_url(){return img_url;}
    public String getImg_width(){return img_width;}
    public String getImg_height(){return img_height;}
    public String getTitle(){return title;}
    public String getLocation(){return location;}
    public String getText_date(){return text_date;}
    public String getWeb_url(){return web_url;}

    public void setImg_url(String img_url){this.img_url = img_url;}
    public void setImg_width(String img_width){this.img_width = img_width;}
    public void setImg_height(String img_height){this.img_height = img_height;}
    public void setTitle(String title){this.title = title;}
    public void setLocation(String location){this.location = location;}
    public void setText_date(String text_date){this.text_date = text_date;}
    public void setWeb_url(String web_url){this.web_url = web_url;}
}
