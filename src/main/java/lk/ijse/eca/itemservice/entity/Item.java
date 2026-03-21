package lk.ijse.eca.itemservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    private String itemId;

    /** Display name of the item (e.g. "Wireless Earbuds Pro") */
    private String name;

    /** Short one-line tagline shown on cards */
    private String shortDescription;

    /** Full rich description shown on item detail page */
    private String description;

    /** Price in LKR */
    private Double price;

    /** Category (e.g. Electronics, Food, Clothing) */
    private String category;

    /** Available stock count */
    private Integer stock;

    /** List of Base64-encoded image strings (up to 4) */
    private List<String> images;

    /** ISO 8601 timestamp of creation */
    private String createdAt;
}
