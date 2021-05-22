package maksym.kruhovykh.app;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SpringFrameworkTutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFrameworkTutorApplication.class, args);
    }
}
