package kz.kcell.rest.dto;

/**
 *
 * @author daniyar.artykov
 */
public class LastValue {

    private final String time;
    private final String value;

    public LastValue(String time, String value) {
        this.time = time;
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }
    
}
