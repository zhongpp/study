package com.zpp.springboot.util;

import com.zpp.springboot.json.Body;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhongpp
 * on 2018/6/7.
 */
@Component
public class BodyValidation implements InitializingBean, ApplicationContextAware, ConstraintValidatorFactory,
        org.springframework.validation.SmartValidator {

    private Validator validator;
    private ApplicationContext applicationContext;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate(target, errors, Default.class);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (validator == null) {
            return;
        }
        Class<?>[] classes = new Class[validationHints.length];
        for (int i = 0; i < validationHints.length; i++) {
            classes[i] = (Class<?>) validationHints[i];
        }

        if (target.getClass().equals(Body.class)) {
            Body body = (Body) target;
            for (String key : body.getMapping().keySet()) {
                Object object = body.get(key);
                if (object instanceof List) {
                    for (int i = 0; i < ((List) object).size(); i++) {
                        valid(key + "[" + i + "].", ((List) object).get(i), errors, classes);
                    }
                } else {
                    valid(key + ".", body.get(key), errors, classes);
                }
            }
        } else {
            valid(errors.getObjectName() + ".", target, errors, classes);
        }
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        Map<?, T> beans = applicationContext.getBeansOfType(key);
        if (beans.isEmpty()) {
            try {
                return key.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (beans.size() > 1) {
            throw new RuntimeException("Beans must be Singleton!");
        }
        return beans.values().iterator().next();
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        System.out.println("The bean of BodyValidation is no longer used!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().constraintValidatorFactory(this)
                .buildValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    private void valid(String pathName, Object object, Errors errors, Class[] classes) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, classes);
        Errors error = new BeanPropertyBindingResult(object, errors.getObjectName());
        for (ConstraintViolation<Object> violation : constraintViolations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.rejectValue(propertyPath, pathName + propertyPath, message);
        }
        if (error.hasErrors()) {
            errors.addAllErrors(error);
        }
    }

}