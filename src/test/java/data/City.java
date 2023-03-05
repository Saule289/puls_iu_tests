package data;



public enum City {
    BRYANSK("Брянск"),
    VOLGOGRAD("Волгоград"),
    VORONEZH("Воронеж"),
    EKATERINBURG("Екатеринбург"),
    IRKUTSK("Иркутск");
    private final String desc;

    City(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

