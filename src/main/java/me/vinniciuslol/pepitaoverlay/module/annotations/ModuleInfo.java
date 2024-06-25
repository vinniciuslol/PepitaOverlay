package me.vinniciuslol.pepitaoverlay.module.annotations;

import me.vinniciuslol.pepitaoverlay.module.enums.ModuleCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    String name();
    ModuleCategory category();
    int key() default 0;
}
