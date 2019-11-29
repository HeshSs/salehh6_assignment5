import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

abstract class Plan {
    String name;
    long premium;
    long maxCoveragePerClaim;
    long deductible;
    RangeCriterion customerAgeCriterion = new RangeCriterion();
    RangeCriterion customerIncomeCriterion = new RangeCriterion();
    RangeCriterion customerWealthCriterion = new RangeCriterion();

    Plan(HashMap<String, ArrayList<Tag>> tags) {
        name = tags.get("NAME").get(0).getValue();
        premium = Integer.parseInt(tags.get("PREMIUM").get(0).getValue());
        maxCoveragePerClaim = Integer.parseInt(tags.get("MAX_COVERAGE_PER_CLAIM").get(0).getValue());
        deductible = Integer.parseInt(tags.get("DEDUCTIBLE").get(0).getValue());

        ArrayList<Tag> ageTags = tags.get("CUSTOMER.AGE");
        if (ageTags != null) {
            int ageTagSize = ageTags.size();
            customerAgeCriterion.addCriterion(ageTags.get(ageTagSize-1));
            if (ageTagSize > 1) {
                customerAgeCriterion.addCriterion(ageTags.get(ageTagSize-2));
            }
        }

        ArrayList<Tag> incomeTags = tags.get("CUSTOMER.INCOME");
        if (incomeTags != null) {
            int incomeTagSize = incomeTags.size();
            customerIncomeCriterion.addCriterion(incomeTags.get(incomeTagSize-1));
            if (incomeTagSize > 1) {
                customerAgeCriterion.addCriterion(incomeTags.get(incomeTagSize-2));
            }
        }

    }

    abstract boolean isEligible(Insurable insurable, Date date);

    abstract ArrayList<? extends Insurable> getInsuredItems(Customer customer, Database database);

    abstract Insurable getInsuredItem(String insurableID, Database database);

    boolean isEligible(Customer customer, Date currentDate, Database database) {
        // Extracting the age of the customer
        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localBirthDate = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Period.between gets the time difference of 2 dates in (years, month and days)
        Period period = Period.between(localBirthDate, localCurrentDate);       /* Added for Assignment 5 Part 1 */
        long age = period.getYears();
        // Checking if the age is in the range.
        if (!customerAgeCriterion.isInRange(age))
            return false;
        //Calculate wealth of the customer
        database.setCustomerWealth(customer);       /* Added for Assignment 5 Part 2 */
        // Checking if the customer wealth is in the range.
        if (!customerWealthCriterion.isInRange(customer.getWealth()))
            return false;
        // Checking if the income is in the range.
        return customerIncomeCriterion.isInRange(customer.getIncome());
    }

    String getName() {
        return name;
    }

    long getPremium() {
        return premium;
    }

    long getMaxCoveragePerClaim() {
        return maxCoveragePerClaim;
    }

    long getDeductible() {
        return deductible;
    }
}
