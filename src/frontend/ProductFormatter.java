package frontend;

import model.Product;

public class ProductFormatter {
	
	public static String formatProductAsCard(Product product) {
        StringBuilder card = new StringBuilder();
        card.append("====================================\n");
        card.append("Product ID     : ").append(product.getProductId()).append("\n");
        card.append("Product Name   : ").append(product.getProductName()).append("\n");
        card.append("Details        : ").append(product.getProductDetails()).append("\n");
        card.append("Cost           : $").append(String.format("%.2f", product.getCost())).append("\n");
        card.append("====================================\n");
        return card.toString();
    }

}
