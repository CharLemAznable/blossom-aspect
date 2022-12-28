package blossom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Blossom {

    Level value() default Level.INFO;

    enum Level {

        ERROR(40),
        WARN(30),
        INFO(20),
        DEBUG(10),
        TRACE(0);

        private final int levelInt;

        Level(int i) {
            this.levelInt = i;
        }

        public int toInt() {
            return this.levelInt;
        }
    }
}
