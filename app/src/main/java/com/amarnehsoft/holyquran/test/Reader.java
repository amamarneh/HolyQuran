package com.amarnehsoft.holyquran.test;


public enum Reader {

    mahermuaiqly("ar.mahermuaiqly", "المعيقلي", "ماهر المعيقلي" , "https://www.tvquran.com/uploads/authors/images/ماهر%20المعيقلي.jpg"),
    ahmedajamy("ar.ahmedajamy", "العجمي","أحمد العجمي", "https://www.tvquran.com/uploads/authors/images/أحمد%20بن%20علي%20العجمي.jpg"),
    abdullahbasfar("ar.abdullahbasfar", "بصفر", "عبد الله بصفر", "https://www.tvquran.com/uploads/authors/images/عبد%20الله%20بصفر.jpg"),
    abdurrahmaansudais("ar.abdurrahmaansudais", "السديس", "عبد الرحمن السديس", "https://www.tvquran.com/uploads/authors/images/عبد%20الرحمن%20السديس.jpg"),
    abdulsamad("ar.abdulsamad", "عبد الباسط", "عبد الباسط عبد الصمد", "https://gp1.wac.edgecastcdn.net/802892/http_public_production/artists/images/4002770/original/resize:248x186/crop:x0y4w220h165/hash:1467527037/1393983561_abdulbasit_1.jpg?1467527037"),
    shaatree("ar.shaatree", "الشاطري", "أبو بكر الشاطري", "https://www.tvquran.com/uploads/authors/images/شيخ%20أبو%20بكر%20الشاطري.jpg"),
    alafasy("ar.alafasy", "العفاسي", "مشاري راشد العفاسي", "https://www.tvquran.com/uploads/authors/images/مشاري%20العفاسي.jpg"),
    hanirifai("ar.hanirifai", "الرفاعي", "هاني الرفاعي", "https://www.tvquran.com/uploads/authors/images/هاني%20الرفاعي.jpg"),
    husary("ar.husary", "الحصري", "محمود خليل الحصري", "https://i.ytimg.com/vi/tkYeLaEzIzk/maxresdefault.jpg"),
    husarymujawwad("ar.husarymujawwad", "الحصري(مجود)", "محمود خليل الحصري(مجود)", "https://i.ytimg.com/vi/tkYeLaEzIzk/maxresdefault.jpg"),
    hudhaify("ar.hudhaify", "الحذيفي", "علي عبد الرحمن الحذيفي", "https://s3-eu-west-1.amazonaws.com/content.argaamnews.com/c0922eb9-d842-4993-b6ec-406451b620b1.jpg"),
    ibrahimakhbar("ar.ibrahimakhbar", "ابراهيم الاخضر", "ابراهيم الاخضر", "https://i.ytimg.com/vi/fltmA1-_CZY/hqdefault.jpg"),
    muhammadayyoub("ar.muhammadayyoub", "محمد أيوب", "محمد أيوب", "https://www.nmisr.com/wp-content/uploads/2016/04/الشيخ-محمد-ايوب.jpg"),
    muhammadjibreel("ar.muhammadjibreel", "محمد جبريل", "محمد جبريل", "https://www.tvquran.com/uploads/authors/images/محمد%20جبريل.jpg"),
    saoodshuraym("ar.saoodshuraym", "الشريم", "سعود الشريم", "https://www.tvquran.com/uploads/authors/images/سعود%20الشريم.jpg"),
    parhizgar("ar.parhizgar", "Parhizgar", "Shahriar Parhizgar", "https://i1.sndcdn.com/artworks-000044326606-on7ord-t500x500.jpg");

    private String id;
    private String name;
    private String fullName;
    private String imgUrl;

    Reader(String id, String name, String fullName, String imgUrl){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.fullName = fullName;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getFullName() {
        return fullName;
    }
}
