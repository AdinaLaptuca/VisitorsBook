package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

class SignupContract {
    interface View extends MvpContract.View {
        void signedUpSuccesfull();

        void rolesFetched(List<String> roles);

        void companiesFetched();
    }

    interface Presenter extends MvpContract.Presenter<SignupContract.View> {

        void fetchRoles();

        void fetchCompanies();

        List<String> getCompanies();

        List<String> getOfficesForCompany(String company);

        Office getOffice(String companyName, String officeName);

        void trySignUp(String email, String password);
    }
}
