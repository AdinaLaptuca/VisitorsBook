package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

class ExternalRegisterPresenter implements ExternalRegisterContract.Presenter {
    private ExternalRegisterContract.View view;

    ExternalRegisterPresenter(ExternalRegisterContract.View view) {
        this.view = view;
    }
}
