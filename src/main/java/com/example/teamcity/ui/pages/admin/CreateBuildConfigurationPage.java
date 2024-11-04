package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildConfigurationPage extends CreateBasePage{

    private static final String BUILD_CONFIGURATION_SHOW_MODE = "createBuildTypeMenu";

    private SelenideElement buildConfigurationNameInput = $("#buildTypeName");
    public SelenideElement buildTypeNameError = $("#error_buildTypeName");

    public static CreateBuildConfigurationPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_CONFIGURATION_SHOW_MODE), CreateBuildConfigurationPage.class);
    }

    public CreateBuildConfigurationPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public CreateBuildConfigurationPage setupBuildConfiguration(String buildConfigurationName) {
        buildConfigurationNameInput.val(buildConfigurationName);
        submitButton.click();
        return this;
    }
}