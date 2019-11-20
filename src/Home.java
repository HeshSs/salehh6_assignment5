import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

class Home extends Insurable {
    private String postalCode;
    private Date buildDate;

    static final String inputTag = "HOME";

    Home(HashMap<String, Tag> tags) throws ParseException {
        super(tags);
        postalCode = tags.get("POSTAL_CODE").getValue();
        buildDate = Utils.convertDate(tags.get("BUILD_DATE").getValue());
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public static String getInputTag() {
        return inputTag;
    }
}
