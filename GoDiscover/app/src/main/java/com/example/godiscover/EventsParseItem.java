package com.example.godiscover;

public class EventsParseItem {
    private String img_url;
    private String img_width;
    private String img_height;
    private String title;

    public EventsParseItem(){}

    public EventsParseItem(String img_url, String img_width, String img_height, String title)
    {
        this.img_url = img_url;
        this.img_width = img_width;
        this.img_height = img_height;
        this.title = title;
    }

    public String getImg_url(){return img_url;}
    public String getImg_width(){return img_width;}
    public String getImg_height(){return img_height;}
    public String getTitle(){return title;}

    public void setImg_url(String img_url){this.img_url = img_url;}
    public void setImg_width(String img_width){this.img_width = img_width;}
    public void setImg_height(String img_height){this.img_height = img_height;}
    public void setTitle(String title){this.title = title;}
}
