package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import com.example.teamcity.ui.pages.ProjectPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})
public class CreateBuildConfigurationTest extends BaseUiTest {

    @Test(description = "User should be able to create a build configuration", groups = {"Positive"})
    public void userCreatesBuildConfiguration() {
        // подготовка окружения
        loginAs(testData.getUser());
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // взаимодействие с UI
        CreateBuildConfigurationPage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(testData.getBuildType().getName());

        // проверка состояния API
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        // проверка состояния UI
        ProjectPage.open(testData.getProject().getId())
                .buildName.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }

    @Test(description = "User shouldn't be able to create a build configuration without build name", groups = {"Negative"})
    public void userCreatesBuildConfigurationWithoutBuildName() {
        // подготовка окружения
        loginAs(testData.getUser());
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // взаимодействие с UI
        CreateBuildConfigurationPage.open(testData.getProject().getId())
                .createForm(REPO_URL).setupBuildConfiguration("")
                .buildTypeNameError.shouldBe(Condition.exactText("Build configuration name must not be empty"));

        // проверка состояния API
        superUserUncheckRequests.getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}