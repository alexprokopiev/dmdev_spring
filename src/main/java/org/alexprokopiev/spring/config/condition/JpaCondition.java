package org.alexprokopiev.spring.config.condition;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JpaCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            context.getClassLoader().loadClass("org.postgresql.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
