package lk.ijse.eca.itemservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    public interface OnCreate {}

    @NotBlank(groups = OnCreate.class, message = "Item ID is required")
    @Pattern(groups = OnCreate.class, regexp = "^[A-Z]+$", message = "Item ID must contain uppercase letters only (A-Z)")
    private String itemId;

    /** Display name for the item */
    private String name;

    /** One-line tagline */
    private String shortDescription;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be zero or greater")
    private Double price;

    /** Category name e.g. Electronics, Food */
    private String category;

    @Min(value = 0, message = "Stock must be zero or greater")
    private Integer stock;

    /** Base64-encoded image strings (max 4) */
    private List<String> images;

    /** ISO 8601 creation timestamp */
    private String createdAt;
}
