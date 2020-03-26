package site.kycer.project.ktool.spi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SPIValue {

    /**
     * value
     *
     * @return the value
     */
    String value() default "";
}
