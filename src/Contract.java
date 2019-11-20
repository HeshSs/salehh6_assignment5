import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

class Contract {
    private String contractName;
    private String customerName;
    private String planName;
    private Date startDate;
    private Date endDate;

    static final String inputTag = "CONTRACT";

    Contract(HashMap<String, Tag> tags) throws ParseException {
        contractName = tags.get("CONTRACT_NAME").getValue();
        customerName = tags.get("CUSTOMER_NAME").getValue();
        planName = tags.get("PLAN_NAME").getValue();
        startDate = Utils.convertDate(tags.get("START_DATE").getValue());
        endDate = Utils.convertDate(tags.get("END_DATE").getValue());
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPlanName() {
        return planName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getContractName() {
        return contractName;
    }
}
