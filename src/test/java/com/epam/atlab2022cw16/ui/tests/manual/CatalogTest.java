package com.epam.atlab2022cw16.ui.tests.manual;

import com.epam.atlab2022cw16.ui.annotations.JiraTicketsLink;
import com.epam.atlab2022cw16.ui.application.constants.Constants.SortParams;
import com.epam.atlab2022cw16.ui.application.constants.Constants.Color;
import com.epam.atlab2022cw16.ui.application.models.Product;
import com.epam.atlab2022cw16.ui.application.pages.WomenCatalogPage;
import com.epam.atlab2022cw16.ui.utils.TestListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JiraTicketsLink(id = 16307,
        description = "Test checks sorting and filtration on the site",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16307")
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({
        @Tag("ui"),
        @Tag("manual")
})
public class CatalogTest extends AbstractBaseTest {

    private static final List<String> selectedFilter = new ArrayList<>();
    private static final WomenCatalogPage page = new WomenCatalogPage(getWebDriver());
    private final Color filterByColor = Color.YELLOW;
    private final String selectedFilterBorderColor = Color.RED.getColorRGB();

    @Order(1)
    @Test
    void shouldOpenCatalogPage() {
        page.openPage();
        assertTrue(page.isPageTitleValid());
    }

    @Order(2)
    @Test
    void shouldSortByPriceDesc() {
        List<Product> sortedProducts = page.sortByType(SortParams.PRICE_DESC).getItemsInCatalog();
        List<Double> actualProductsPrices = sortedProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(50.99, 30.5, 28.98, 27.0, 26.0, 16.51, 16.4);
        assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(3)
    @Test
    void shouldSortByNameDesc() {
        List<Product> sortedProducts = page.sortByType(SortParams.NAME_DESC).getItemsInCatalog();
        List<String> actualProductsNames = sortedProducts.stream().map(Product::getProductName).collect(Collectors.toList());
        List<String> expectedProductsNames = Arrays.asList("Printed Summer Dress", "Printed Summer Dress", "Printed Dress", "Printed Dress", "Printed Chiffon Dress", "Faded Short Sleeve T-shirts", "Blouse");
        assertEquals(expectedProductsNames, actualProductsNames);
    }

    @Order(4)
    @Test
    void shouldSortByNameAsc() {
        List<Product> sortedProducts = page.sortByType(SortParams.NAME_ASC).getItemsInCatalog();
        List<String> actualProductsNames = sortedProducts.stream().map(Product::getProductName).collect(Collectors.toList());
        List<String> expectedProductsNames = Arrays.asList("Blouse", "Faded Short Sleeve T-shirts", "Printed Chiffon Dress", "Printed Dress", "Printed Dress", "Printed Summer Dress", "Printed Summer Dress");
        assertEquals(expectedProductsNames, actualProductsNames);
    }

    @Order(5)
    @Test
    void shouldSortByPriceAsc() {
        List<Product> sortedProducts = page.sortByType(SortParams.PRICE_ASC).getItemsInCatalog();
        List<Double> actualProductsPrices = sortedProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 16.51, 26.0, 27.0, 28.98, 30.5, 50.99);
        assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(6)
    @Test
    void shouldFilterByColor() {
        List<Product> filteredProducts = page.filterByColor(filterByColor.getColorHex()).getItemsInCatalog();
        List<List<String>> productsColors = filteredProducts.stream().map(Product::getProductColor).collect(Collectors.toList());
        selectedFilter.add("color: " + filterByColor.name().toLowerCase(Locale.ROOT));
        assertTrue(productsColors.stream().allMatch(colorList -> {
            boolean isContained = false;
            for (String color : colorList) {
                isContained = color.contains(filterByColor.getColorRGB());
                if (isContained) {
                    break;
                }
            }
            return isContained;
        }));
    }

    @Order(7)
    @Test
    void shouldChangeBorderColorAppliedFilter() {
        String actualColor = page.getBorderColorFilterByColor(filterByColor.getColorHex());
        assertEquals(selectedFilterBorderColor, actualColor);
    }


    @Order(8)
    @Test
    void shouldFilterByPrice() {
        List<Product> filteredProducts = page.filterByPrice().getItemsInCatalog();
        List<Double> minMaxPrice = page.getMinMaxPrice();
        selectedFilter.add("price: " + (page.getPriceFilterRange().trim()));
        List<Double> productsPrice = filteredProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        assertEquals(productsPrice.size(), (productsPrice.stream().filter(p -> p > minMaxPrice.get(0) && p < minMaxPrice.get(1)).count()));
    }

    @Order(9)
    @Test
    void shouldDisplayAppliedFiltersInFilterBlock() {
        List<String> filterNamesFromBlock = page.findAppliedFilters();
        assertTrue(selectedFilter.size() == filterNamesFromBlock.size() && selectedFilter.containsAll(filterNamesFromBlock));
    }

    @Order(10)
    @Test
    void shouldDisplayFilterNameInBreadCrumbs() {
        page.removePriceFilter();
        List<String> breadCrumbsLevels = Arrays.asList(page.getBreadCrumbs());
        String nameFilter = filterByColor.name();
        assertTrue(breadCrumbsLevels.stream().anyMatch(p -> p.contains(nameFilter)));
    }

    @Order(11)
    @Test
    void shouldSaveSortedConditionWithAppliedFilter() {
        List<Product> filteredProducts = page.getItemsInCatalog();
        List<Double> actualProductsPrices = filteredProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 28.98, 30.5);
        assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(12)
    @Test
    void shouldSaveOrderBySortAfterDeleteAllFilters() {
        page.removeAllFilters();
        List<Double> actualProductsPrices = page.getItemsInCatalog().stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 16.51, 26.0, 27.0, 28.98, 30.5, 50.99);
        assertEquals(expectedProductsPrices, actualProductsPrices);
    }
}
