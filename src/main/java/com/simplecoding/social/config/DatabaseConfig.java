import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {
    //heroku create@Value("${spring.datasource.url}")
//  @Value("jdbc:postgresql://localhost:5432/social_network")
//  private String dbUrl;
//
//  @Bean
//  public DataSource dataSource() {
//      HikariConfig config = new HikariConfig();
//      config.setJdbcUrl(dbUrl);
//      return new HikariDataSource(config);
//  }
}
