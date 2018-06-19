package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

class SignupContract {
    interface View extends MvpContract.View {
        void signedUpSuccesfull();

        void dataFetched();
    }

    interface Presenter extends MvpContract.Presenter<SignupContract.View> {

        void getData();

        List<String> getCompanies();

        List<String> getOfficesForCompany(String company);

        void trySignUp(String email, String password);

        Office getOffice(String companyName, String officeName);
    }
}
