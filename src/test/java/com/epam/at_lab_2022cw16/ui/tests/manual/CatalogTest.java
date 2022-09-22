package com.epam.at_lab_2022cw16.ui.tests.manual;

import com.epam.at_lab_2022cw16.annotations.JiraTicketsLink;
import com.epam.at_lab_2022cw16.ui.constants.ColorHEX;
import com.epam.at_lab_2022cw16.ui.constants.ColorRGB;
import com.epam.at_lab_2022cw16.ui.constants.PageTitles;
import com.epam.at_lab_2022cw16.ui.constants.SortParams;
import com.epam.at_lab_2022cw16.ui.model.Product;
import com.epam.at_lab_2022cw16.ui.page.CatalogPage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@JiraTicketsLink(id = 16307,
        description = "Test check sorting and filtration on the site",
        url = "https://jira.epam.com/jira/browse/EPMFARMATS-16307")

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatalogTest extends AbstractBaseTest {

    private final ColorHEX filterByColorHEX = ColorHEX.YELLOW;
    private final ColorRGB colorRGB = ColorRGB.YELLOW;
    private final String selectedFilterBorderColor = ColorRGB.RED.getColorRGB();
    private static final List<String> selectedFilter = new ArrayList<>();
    private static CatalogPage page = new CatalogPage(getWebDriver());

    @Order(1)
    @Test
    void shouldOpenCatalogPage() {
        page.openPage();
        Assertions.assertEquals(page.getTitle(), PageTitles.PAGE_WITH_WOMEN_TITLE.getPageTitle());
    }

    @Order(2)
    @Test
    void shouldSortByPriceDesc() {
        List<Product> sortedProducts = page.sortByType(SortParams.PRICE_DESC.getSortParam()).getProducts();
        List<Double> actualProductsPrices = sortedProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(50.99, 30.5, 28.98, 27.0, 26.0, 16.51, 16.4);
        Assertions.assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(3)
    @Test
    void shouldSortByNameDesc() {
        List<Product> sortedProducts = page.sortByType(SortParams.NAME_DESC.getSortParam()).getProducts();
        List<String> actualProductsNames = sortedProducts.stream().map(Product::getProductName).collect(Collectors.toList());
        List<String> expectedProductsNames = Arrays.asList("Printed Summer Dress", "Printed Summer Dress", "Printed Dress", "Printed Dress", "Printed Chiffon Dress", "Faded Short Sleeve T-shirts", "Blouse");
        Assertions.assertEquals(expectedProductsNames, actualProductsNames);
    }

    @Order(4)
    @Test
    void shouldSortByNameAsc() {
        List<Product> sortedProducts = page.sortByType(SortParams.NAME_ASC.getSortParam()).getProducts();
        List<String> actualProductsNames = sortedProducts.stream().map(Product::getProductName).collect(Collectors.toList());
        List<String> expectedProductsNames = Arrays.asList("Blouse", "Faded Short Sleeve T-shirts", "Printed Chiffon Dress", "Printed Dress", "Printed Dress", "Printed Summer Dress", "Printed Summer Dress");
        Assertions.assertEquals(expectedProductsNames, actualProductsNames);
    }

    @Order(5)
    @Test
    void shouldSortByPriceAsc() {
        List<Product> sortedProducts = page.sortByType(SortParams.PRICE_ASC.getSortParam()).getProducts();
        List<Double> actualProductsPrices = sortedProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 16.51, 26.0, 27.0, 28.98, 30.5, 50.99);
        Assertions.assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(6)
    @Test
    void shouldFilterByColor() {
        List<Product> filteredProducts = page.filterByColor(filterByColorHEX.getColorHex()).getProducts();
        List<List<String>> productsColors = filteredProducts.stream().map(Product::getProductColor).collect(Collectors.toList());
        selectedFilter.add("color: " + filterByColorHEX.name().toLowerCase(Locale.ROOT));
        Assertions.assertTrue(productsColors.stream().allMatch(colorList -> {
            boolean isContained = false;
            for (String color : colorList) {
                isContained = color.contains(colorRGB.getColorRGB());
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
        String actualColor = page.getBorderColorFilterByColor(filterByColorHEX.getColorHex());
        Assertions.assertEquals(selectedFilterBorderColor, actualColor);
    }


    @Order(8)
    @Test
    void shouldFilterByPrice() {
        List<Product> filteredProducts = page.filterByPrice().getProducts();
        List<Double> minMaxPrice = page.getMinMaxPrice();
        selectedFilter.add("price: " + (page.getPriceFilterRange().trim()));
        List<Double> productsPrice = filteredProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        Assertions.assertEquals(productsPrice.size(), (productsPrice.stream().filter(p -> p > minMaxPrice.get(0) && p < minMaxPrice.get(1)).count()));
    }

    @Order(9)
    @Test
    void shouldDisplayAppliedFiltersInFilterBlock() {
        List<String> filterNamesFromBlock = page.findAppliedFilters();
        Assertions.assertTrue(selectedFilter.size() == filterNamesFromBlock.size() && selectedFilter.containsAll(filterNamesFromBlock));
    }

    @Order(10)
    @Test
    void shouldDisplayFilterNameInBreadCrumbs() {
        page.removePriceFilter();
        List<String> breadCrumbsLevels = Arrays.asList(page.getBreadCrumbs());
        String nameFilter = filterByColorHEX.name();
        Assertions.assertTrue(breadCrumbsLevels.stream().anyMatch(p -> p.contains(nameFilter)));
    }

    @Order(11)
    @Test
    void shouldSaveSortedConditionWithAppliedFilter() {
        List<Product> filteredProducts = page.getProducts();
        List<Double> actualProductsPrices = filteredProducts.stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 28.98, 30.5);
        Assertions.assertEquals(expectedProductsPrices, actualProductsPrices);
    }

    @Order(12)
    @Test
    void shouldSaveOrderBySortAfterDeleteAllFilters() {
        page.removeAllFilters();
        List<Double> actualProductsPrices = page.getProducts().stream().map(Product::getProductPrice).collect(Collectors.toList());
        List<Double> expectedProductsPrices = Arrays.asList(16.4, 16.51, 26.0, 27.0, 28.98, 30.5, 50.99);
        Assertions.assertEquals(expectedProductsPrices, actualProductsPrices);
    }
}
