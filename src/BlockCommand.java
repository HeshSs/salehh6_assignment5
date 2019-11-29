import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class BlockCommand extends Command {
    private String blockType;
    private HashMap<String, Tag> tags = new HashMap<>();
    private HashMap<String, ArrayList<Tag>> planTags = new HashMap<>();

    BlockCommand(String blockType) {
        this.blockType = blockType;
    }

    void addTag(Tag tag) {
        tags.put(tag.getName(), tag);
    }
    // this allows classes like CarPlan, HomePlan and Company to have more than one criteria - Added for Assignment 5 Part 1
    void addTagToList(Tag tag) {
        if (planTags.get(tag.getName()) == null) {
            planTags.put(tag.getName(), new ArrayList<Tag>());
        }
        planTags.get(tag.getName()).add(tag);
    }

    String getBlockType() {
        return blockType;
    }

    @Override
    void run(Database database) throws ParseException {
        if (blockType.equals(Customer.inputTag)) {
            database.insertCustomer(new Customer(tags));
        } if (blockType.equals(Home.inputTag)) {
            database.insertHome(new Home(tags));
        } if (blockType.equals(Car.inputTag)) {
            database.insertCar(new Car(tags));
        } if (blockType.equals(Company.inputTag)) {
            database.insertCompany(new Company(planTags));
        } if (blockType.equals(Claim.inputTag)) {
            Claim claim = new Claim(tags);
            database.insertClaim(claim);
            if (processClaim(claim, database)) {
                claim.setSuccessful(true);
                System.out.println("Claim on " + Utils.formattedDate(claim.getDate())
                        + " for contract " + claim.getContractName() + " was successful.");
            } else {
                claim.setSuccessful(false);
                System.out.println("Claim on " + Utils.formattedDate(claim.getDate())
                        + " for contract " + claim.getContractName() + " was not successful.");
            }
        } if (blockType.equals(Contract.inputTag)) {
            database.insertContract(new Contract(tags));
        } if (blockType.equals(HomePlan.inputTag)) {
            database.insertPlan(new HomePlan(planTags));
        } if (blockType.equals(CarPlan.inputTag)) {
            database.insertPlan(new CarPlan(planTags));
        }
    }

    private boolean processClaim(Claim claim, Database database) {
        Contract contract = database.getContract(claim.getContractName());
        Customer customer = database.getCustomer(contract.getCustomerName());
        Plan plan = database.getPlan(contract.getPlanName());
        Insurable insurable = plan.getInsuredItem(claim.getInsurableID(), database);

        /* If INSURABLE_ID was not found or was from the wrong type (car/home).
        For example, if INSURABLE_ID corresponds to a car but the plan
        corresponds to a home then insurable would be null. */
        if (insurable == null)
            return false;

        // If the claimed item is not owned by the owner.
        if (!insurable.getOwnerName().equals(customer.getName()))
            return false;

        // If the claimed amount is higher than covered by the plan.
        if (plan.getMaxCoveragePerClaim() < claim.getAmount())
            return false;

        // If the claim date is not in the contract period.
        if (claim.getDate().after(contract.getEndDate()) || claim.getDate().before(contract.getStartDate()))
            return false;

        // If the customer was not eligible.
        if (!plan.isEligible(customer, claim.getDate(), database))      /* database added for Assignment 5 Part 2 */
            return false;

        return plan.isEligible(insurable, claim.getDate());
    }
}
