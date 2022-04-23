package com.Haven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/function/youthlearn/jiangxi/getcourse").setViewName("/youthlearn");
//        registry.addViewController("/user/registration").setViewName("/pages/examples/register");
//        registry.addViewController("/login").setViewName("/pages/examples/login");
//        registry.addViewController("/pages/calendar.html").setViewName("/pages/calendar");
//        registry.addViewController("/pages/gallery.html").setViewName("/pages/gallery");
//        registry.addViewController("/pages/kanban.html").setViewName("/pages/kanban");
//        registry.addViewController("/pages/widgets.html").setViewName("/pages/widgets");
//        registry.addViewController("/pages/charts/flot.html").setViewName("pages/charts/flot");
//        registry.addViewController("/pages/charts/inline.html").setViewName("pages/charts/inline");
//        registry.addViewController("/pages/charts/uplot.html").setViewName("pages/charts/uplot");
//        registry.addViewController("/pages/charts/chartjs.html").setViewName("pages/charts/chartjs");
//        registry.addViewController("/pages/examples/404.html").setViewName("pages/examples/404");
//        registry.addViewController("/pages/examples/500.html").setViewName("pages/examples/500");
//        registry.addViewController("/pages/examples/blank.html").setViewName("pages/examples/blank");
//        registry.addViewController("/pages/examples/contact-us.html").setViewName("pages/examples/contact-us");
//        registry.addViewController("/pages/examples/contacts.html").setViewName("pages/examples/contacts");
//        registry.addViewController("/pages/examples/e-commerce.html").setViewName("pages/examples/e-commerce");
//        registry.addViewController("/pages/examples/faq.html").setViewName("pages/examples/faq");
//        registry.addViewController("/pages/examples/forgot-password.html").setViewName("pages/examples/forgot-password");
//        registry.addViewController("/pages/examples/forgot-password-v2.html").setViewName("pages/examples/forgot-password-v2");
//        registry.addViewController("/pages/examples/invoice.html").setViewName("pages/examples/invoice");
//        registry.addViewController("/pages/examples/invoice-print.html").setViewName("pages/examples/invoice-print");
//        registry.addViewController("/pages/examples/language-menu.html").setViewName("pages/examples/language-menu");
//        registry.addViewController("/pages/examples/legacy-user-menu.html").setViewName("pages/examples/legacy-user-menu");
//        registry.addViewController("/pages/examples/lockscreen.html").setViewName("pages/examples/lockscreen");
//        registry.addViewController("/pages/examples/login.html").setViewName("pages/examples/login");
//        registry.addViewController("/pages/examples/login-v2.html").setViewName("pages/examples/login-v2");
//        registry.addViewController("/pages/examples/pace.html").setViewName("pages/examples/pace");
//        registry.addViewController("/pages/examples/profile.html").setViewName("pages/examples/profile");
//        registry.addViewController("/pages/examples/project-add.html").setViewName("pages/examples/project-add");
//        registry.addViewController("/pages/examples/project-detail.html").setViewName("pages/examples/project-detail");
//        registry.addViewController("/pages/examples/project-edit.html").setViewName("pages/examples/project-edit");
//        registry.addViewController("/pages/examples/projects.html").setViewName("pages/examples/projects");
//        registry.addViewController("/pages/examples/recover-password.html").setViewName("pages/examples/recover-password");
//        registry.addViewController("/pages/examples/recover-password-v2.html").setViewName("pages/examples/recover-password-v2");
//        registry.addViewController("/pages/examples/register.html").setViewName("pages/examples/register");
//        registry.addViewController("/pages/examples/register-v2.html").setViewName("pages/examples/register-v2");
    }
}
