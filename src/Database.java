import java.util.ArrayList;

class Database {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<Home> homes = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Plan> plans = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();
    private ArrayList<Claim> claims = new ArrayList<>();

    void insertHome(Home home) {
        homes.add(home);
    }

    void insertCar(Car car) { cars.add(car); }

    void insertCompany(Company company) {
        companies.add(company);
    }

    void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    void insertPlan(Plan plan) {
        plans.add(plan);
    }

    void insertClaim(Claim claim) {
        claims.add(claim);
    }

    void insertContract(Contract contract) {
        contracts.add(contract);
    }

    Plan getPlan(String name) {
        for (Plan plan : plans) {
            if (plan.name.equals(name))
                return plan;
        }
        return null;
    }

    Customer getCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    Contract getContract(String name) {
        for (Contract contract : contracts) {
            if (contract.getContractName().equals(name))
                return contract;
        }
        return null;
    }

    // Calculates and sets customer's wealth - Added for Assignment 5 Part 2
    void setCustomerWealth(Customer customer) {
        long wealth = 0;
        // check if the customer owns any companies
        for (Company company : companies) {
            if (company.getOwnerName().equals(customer.getName())) {
                wealth += company.getValue();
            }
        }
        // check if the customer owns a company that owns another company
        for (Company company : companies) {
            for (Company com : companies) {
                if (company.getOwnerName().equals(customer.getName()) && company.getCompanyName().equals(com.getOwnerName())) {
                    wealth += com.getValue();
                }
            }
        }
        // check if the customer owns any homes
        for (Home home : homes) {
            if (home.ownerName.equals(customer.getName())) {
                wealth += home.value;
            }
        }
        // check if the customer owns any cars
        for (Car car : cars) {
            if (car.ownerName.equals(customer.getName())) {
                wealth += car.value;
            }
        }
        customer.setWealth(wealth);
    }

    ArrayList<Company> getCompaniesByOwnerName(String ownerName) {
        ArrayList<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getOwnerName().equals(ownerName))
                result.add(company);
        }
        return companies;
    }

    /* This function has been updated to output a list
    of homes rather than a single home. In other words,
    an owner may own multiple homes.
     */
    ArrayList<Home> getHomesByOwnerName(String ownerName) {
        ArrayList<Home> result = new ArrayList<>();
        for (Home home : homes) {
            if (home.getOwnerName().equals(ownerName))
                result.add(home);
        }
        return result;
    }


    /* This function has been updated to output a list
    of homes rather than a single home. In other words,
    an owner may own multiple homes.
     */
    ArrayList<Car> getCarsByOwnerName(String ownerName) {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getOwnerName().equals(ownerName))
                result.add(car);
        }
        return cars;
    }

    long totalClaimAmountByCustomer(String customerName) {
        long totalClaimed = 0;
        for (Claim claim : claims) {
            if (getContract(claim.getContractName()).getCustomerName().equals(customerName))
                totalClaimed += claim.getAmount();
        }
        return totalClaimed;
    }

    long totalReceivedAmountByCustomer(String customerName) {
        long totalReceived = 0;
        for (Claim claim : claims) {
            Contract contract = getContract(claim.getContractName());
            if (contract.getCustomerName().equals(customerName)) {
                if (claim.wasSuccessful()) {
                    long deductible = getPlan(contract.getPlanName()).getDeductible();
                    totalReceived += Math.max(0, claim.getAmount() - deductible);
                }
            }
        }
        return totalReceived;
    }

    public Insurable getCarByPlateNumber(String insurableID) {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(insurableID))
                return car;
        }
        return null;
    }

    public Insurable getHomeByPostalCode(String insurableID) {
        for (Home home : homes) {
            if (home.getPostalCode().equals(insurableID))
                return home;
        }
        return null;
    }
}
