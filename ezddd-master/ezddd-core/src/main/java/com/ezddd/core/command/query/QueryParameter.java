package com.ezddd.core.command.query;


import com.ezddd.core.command.query.constraints.Constraint;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryParameter {
    /**
     * The name of the variable to bind to.
     * @return
     */
    String value();

    /**
     * The constraints of the variable to bind to.
     * @return
     */
    Constraint[] constraints() default {};
}
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//
//public class QueryParameters implements Serializable {
//    private static final long serialVersionUID = 1744223117560099735L;
//    private final Map<String, Object> parameters;
//
//    private QueryParameters() {
//        parameters = new HashMap<>(16);
//    }
//
//    private QueryParameters(Map<String, Object> parameters) {
//        this.parameters = parameters;
//    }
//
//    private void addParameter(String key, Object value) {
//        this.parameters.put(key, value);
//    }
//
//    public <T> T getParameter(String key) throws GetParameterFailedException {
//        //TODO
//        return (T) new Object();
//    }
//
//    private void validate() {
//
//    }
//}
