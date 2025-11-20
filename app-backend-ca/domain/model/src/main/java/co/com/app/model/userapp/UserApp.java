package co.com.app.model.userapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserApp {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
