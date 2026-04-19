package com.ness.automation.core.driver;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Logs every Selenium {@link WebDriver} / {@link org.openqa.selenium.WebElement} /
 * navigation / alert / options call into the current Allure test as {@link Allure#step(String)} entries.
 */
public final class AllureLoggingWebDriverListener implements WebDriverListener {

    private static final int MAX_ARG_STRING = 400;

    private AllureLoggingWebDriverListener() {
    }

    /**
     * @return a decorated driver that reports each Selenium API call to Allure.
     */
    public static WebDriver wrap(WebDriver delegate) {
        return new EventFiringDecorator<>(new AllureLoggingWebDriverListener()).decorate(delegate);
    }

    @Override
    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyCall(target, method, args, result);
        Allure.step(describeCall(target, method, args));
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);
        Throwable cause = e.getCause() != null ? e.getCause() : e;
        String msg = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName();
        Allure.step("Selenium failed: " + describeCall(target, method, args) + " → " + msg, Status.BROKEN);
    }

    private static String describeCall(Object target, Method method, Object[] args) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(shortTypeName(target)).append('.').append(method.getName()).append('(');
        if (args == null || args.length == 0) {
            sb.append(')');
            return sb.toString();
        }
        if ("sendKeys".equals(method.getName())) {
            sb.append(maskSendKeysArgs(args));
        } else {
            sb.append(formatArgs(args));
        }
        sb.append(')');
        return truncate(sb.toString(), MAX_ARG_STRING);
    }

    private static String maskSendKeysArgs(Object[] args) {
        int total = 0;
        for (Object a : args) {
            if (a instanceof CharSequence cs) {
                total += cs.length();
            }
        }
        return total > 0 ? "«" + total + " chars»" : "";
    }

    private static String formatArgs(Object[] args) {
        return Arrays.stream(args)
                .map(AllureLoggingWebDriverListener::formatOneArg)
                .collect(Collectors.joining(", "));
    }

    private static String formatOneArg(Object arg) {
        if (arg == null) {
            return "null";
        }
        if (arg instanceof CharSequence cs) {
            return '"' + truncate(cs.toString(), 200) + '"';
        }
        if (arg instanceof byte[] bytes) {
            return "(byte[" + bytes.length + "])";
        }
        String s = arg.toString();
        return truncate(s, 200);
    }

    private static String shortTypeName(Object target) {
        if (target == null) {
            return "null";
        }
        Class<?> c = target.getClass();
        String simple = c.getSimpleName();
        if (!simple.isEmpty()) {
            return simple;
        }
        String name = c.getName();
        int dot = name.lastIndexOf('.');
        return dot >= 0 ? name.substring(dot + 1) : name;
    }

    private static String truncate(String s, int max) {
        if (s == null || s.length() <= max) {
            return s;
        }
        return s.substring(0, max) + "…";
    }
}
