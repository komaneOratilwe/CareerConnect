package za.ac.cput.factory;

import za.ac.cput.domain.Company;

public class CompanyFactory {

    public static Company createCompany(String companyId, String name, String industry, String contactEmail) {

        if (isNullOrEmpty(companyId) ||
                isNullOrEmpty(name) ||
                isNullOrEmpty(industry) ||
                isNullOrEmpty(contactEmail)) {
            return null;
        }

        return new Company.Builder()
                .setCompanyId(companyId)
                .setName(name)
                .setIndustry(industry)
                .setContactEmail(contactEmail)
                .build();
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}