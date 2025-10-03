package co.com.security.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Category {
    private Long id;

    private String name;

    private CategoryStatus status;

    public static enum CategoryStatus {
        ENABLED, DISABLED;
    }
}
