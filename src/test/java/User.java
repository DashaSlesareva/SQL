import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

    public class User {
        private String id;
        private String login;
        private String password;
        private String status;
}
