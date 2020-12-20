package pl.mini.pw.zanieczyszczenie.data.commons;

public enum PollutionType {
    //TODO: move this out of commons
    STANDARD("", "", -1), // INDEX ONLY!
    SO2("dwutlenek siarki", "SO2", 1),
    NO2("dwutlenek azotu", "NO2", 6),
    CO("tlenek węgla", "CO", 8),
    PM10("pył zawieszony PM10", "PM10", 3),
    PM25("pył zawieszony PM2.5", "PM2.5", 69),
    O3("ozon", "O3", 5),
    C6H6("benzen", "C6H6", 10);

    private final String paramName;
    private final String paramCode;
    private final Integer idParam;

    PollutionType(String paramName, String paramCode, Integer idParam) {
        this.paramName = paramName;
        this.paramCode = paramCode;
        this.idParam = idParam;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamCode() {
        return paramCode;
    }

    public Integer getIdParam() {
        return idParam;
    }
}
