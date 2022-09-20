package com.epam.at_lab_2022cw16.ui.tests.bdd.steps;

import com.epam.at_lab_2022cw16.ui.constants.ColorHEX;
import com.epam.at_lab_2022cw16.ui.constants.ColorRGB;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.model.Product;
import com.epam.at_lab_2022cw16.ui.page.CatalogPage;
import com.epam.at_lab_2022cw16.ui.utils.EnvironmentUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogPageStepDefinitions {

    private final WebDriver driver = EnvironmentUtils.getDriver();

    private CatalogPage catalog;

    @Given("the user opens CatalogPage")
    public void openCatalog() {
        catalog = new CatalogPage(driver);
        catalog.openPage();
    }

    @Then("the CatalogPage opened")
    public void theCatalogPageOpened() {
        Assertions.assertEquals(PageTitles.PAGE_WITH_WOMEN_TITLE.getPageTitle(), catalog.getTitle());
    }

    @When("the user click on dropdown list Sort by and select sort {string}")
    public void theUserClickOnDropdownListSortByAndSelectSort(String param) {
        catalog.sortByType(param);
    }

    @Then("all products are displayed on the page according sort {string}")
    public void allProductsAreDisplayedOnThePageAccordingSort(String param) {
        Assertions.assertTrue(sortByParam(param, catalog));
    }

    @When("the catalog sorted by {string}")
    public void theCatalogSortedBy(String sortParam) {
        catalog.sortByType(sortParam);
    }

    @When("the user apply {string} filter")
    public void theUserApplyFilter(String colorFilter) {
        catalog.filterByColor(ColorHEX.valueOf(colorFilter).getColorHex());
    }

    @Then("applied {string} filter marked in the filter block by {string} red color.")
    public void appliedFilterMarkedInTheFilterBlockByRedColor(String filterColor, String borderColor) {
        String actualColor = catalog.getBorderColorFilterByColor(ColorHEX.valueOf(filterColor).getColorHex());
        Assertions.assertEquals(ColorRGB.valueOf(borderColor).getColorRGB(), actualColor);
    }

    @Then("all products filtered by {string} color")
    public void allProductsFilteredByColor(String colorFilter) {
        List<List<String>> productsColors = catalog.getProducts().stream().map(Product::getProductColor).collect(Collectors.toList());
        Assertions.assertTrue(productsColors.stream().allMatch(colorList -> {
            boolean isContained = false;
            for (String color : colorList) {
                isContained = color.contains(ColorRGB.valueOf(colorFilter).getColorRGB());
                if (isContained) {
                    break;
                }
            }
            return isContained;
        }));
    }

    @Then("the breadcrumbs on the filter page contains the name of the applied {string} filter.")
    public void theBreadcrumbsOnTheFilterPageContainsTheNameOfTheAppliedFilter(String filterName) {
        List<String> breadCrumbsLevels = Arrays.asList(catalog.getBreadCrumbs());
        String nameFilter = ColorHEX.valueOf(filterName).name();
        Assertions.assertTrue(breadCrumbsLevels.stream().anyMatch(p -> p.contains(nameFilter)));
    }

    @When("the user apply another one price filter")
    public void theUserApplyAnotherOnePriceFilter() {
        catalog.filterByPrice();
    }

    @Then("the page displays products that match the price filter")
    public void thePageDisplaysProductsThatMatchThePriceFilter() {
        List<Double> minMaxPrice = catalog.getMinMaxPrice();
        List<Double> productsPrice = catalog.getProducts().stream().map(Product::getProductPrice).collect(Collectors.toList());
        Assertions.assertEquals(productsPrice.size(), (productsPrice.stream().filter(p -> p > minMaxPrice.get(0) && p < minMaxPrice.get(1)).count()));
    }

    @And("filters type {string}, {string} and filters name are displayed in the Enabled filters: on the left side of the page.")
    public void filtersTypeAndFiltersNameAreDisplayedInTheEnabledFiltersOnTheLeftSideOfThePage(String price, String color) {
        List<String> selectedFilters = new ArrayList<>();
        selectedFilters.add(price + (catalog.getPriceFilterRange().trim()));
        selectedFilters.add(color);
        List<String> filterNamesFromBlock = catalog.findAppliedFilters();
        Assertions.assertTrue(selectedFilters.size() == filterNamesFromBlock.size() && selectedFilters.containsAll(filterNamesFromBlock));
    }

    @When("the user remove all filters")
    public void theUserRemoveAllFilters() {
        catalog.removeAllFilters().removePriceFilter();
    }

    @Then("the page displays products sorted by the condition from first step {string}.")
    public void thePageDisplaysProductsSortedByTheConditionFromFirstStep(String param) {
        Assertions.assertTrue(sortByParam(param, catalog));
    }

    private static boolean sortByParam(String param, CatalogPage catalog) {
        boolean isEquals;
        if (param.contains("name")) {
            List<String> actualProductsNames = catalog.getProducts().stream().map(Product::getProductName).collect(Collectors.toList());
            List<String> expectedProductsNames;
            if (param.contains("desc")) {
                expectedProductsNames = Arrays.asList("Printed Summer Dress", "Printed Summer Dress", "Printed Dress", "Printed Dress", "Printed Chiffon Dress", "Faded Short Sleeve T-shirts", "Blouse");
            } else {
                expectedProductsNames = Arrays.asList("Blouse", "Faded Short Sleeve T-shirts", "Printed Chiffon Dress", "Printed Dress", "Printed Dress", "Printed Summer Dress", "Printed Summer Dress");
            }
            isEquals = expectedProductsNames.equals(actualProductsNames);
        } else {
            List<Double> actualProductsPrices = catalog.getProducts().stream().map(Product::getProductPrice).collect(Collectors.toList());
            List<Double> expectedProductsPrices;
            if (param.contains("desc")) {
                expectedProductsPrices = Arrays.asList(50.99, 30.5, 28.98, 27.0, 26.0, 16.51, 16.4);
            } else {
                expectedProductsPrices = Arrays.asList(16.4, 16.51, 26.0, 27.0, 28.98, 30.5, 50.99);
            }
            isEquals = expectedProductsPrices.equals(actualProductsPrices);
        }
        return isEquals;
    }

    @When("I add to Compare product with id {int}")
    public void addItemsToCompare(int id) {
        new CatalogPage(driver).addToCompareItemByID(id);
    }

    @When("I click to Compare button")
    public void clickToCompareButton() {
        new CatalogPage(driver).clickCompareButton();
    }
}
