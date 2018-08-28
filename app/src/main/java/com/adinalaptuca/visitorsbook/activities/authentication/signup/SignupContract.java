package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

class SignupContract {
    interface View extends MvpContract.View {
        void signedUpSuccesfull();

        void rolesFetched(List<String> roles);

        void companiesFetched();

        void officesFetched(String companyName, List<String> result);
    }

    interface Presenter extends MvpContract.Presenter<SignupContract.View> {

        void fetchRoles();

        void fetchCompanies();

        List<String> getCompanies();

        List<String> getOfficesForCompany(String company);

        void fetchOfficesForCompany(final String companyName);

        Office getOffice(String companyName, String officeName);

        void trySignUpUserNewCompany(String companyName,
                                     String officeName,
                                     String address,
                                     String firstName,
                                     String lastName,
                                     String employeeRole,
                                     String email,
                                     String password);

        void trySignUpInCompany(final String companyName,
                                final String officeName,
                                final String employeeFirstName,
                                final String employeeLastName,
                                final String employeeRole,
                                final String email,
                                final String password);
    }
}
