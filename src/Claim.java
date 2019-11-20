import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

class Claim {
    private String contractName;
    private long amount;
    private Date date;
    private boolean successful;
    private String InsurableID;

    static final String inputTag = "CLAIM";

    Claim(HashMap<String, Tag> tags) throws ParseException {
        contractName = tags.get("CONTRACT_NAME").getValue();
        InsurableID = tags.get("INSURABLE_ID").getValue();
        date = Utils.convertDate(tags.get("DATE").getValue());
        amount = Long.parseLong(tags.get("AMOUNT").getValue());
    }

    public String getContractName() {
        return contractName;
    }

    public long getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public boolean wasSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getInsurableID() {
        return InsurableID;
    }
}
