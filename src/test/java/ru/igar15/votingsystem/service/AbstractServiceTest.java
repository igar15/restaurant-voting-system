package ru.igar15.votingsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.igar15.votingsystem.TimingExtension;
import ru.igar15.votingsystem.config.AppConfig;
import ru.igar15.votingsystem.config.BeanPropsOverrideConfig;
import ru.igar15.votingsystem.util.ValidationUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppConfig.class, BeanPropsOverrideConfig.class})
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
public abstract class AbstractServiceTest {

    public <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }
}