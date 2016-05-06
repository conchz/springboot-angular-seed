package com.github.lavenderx.config

import groovy.transform.TypeChecked
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.ErrorPage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by lavenderx on 2016-05-05.
 */
@Order(0)
@Configuration
@ComponentScan([
        'com.github.lavenderx.model.dao',
        'com.github.lavenderx.service'])
@PropertySource([
        'classpath:common-config.properties',
        'classpath:${spring.profiles.active}.properties'])
@TypeChecked
class RootConfig {

    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() {
        new EmbeddedServletContainerCustomizer() {
            @Override
            void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, '/views/401.html')
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, '/views/404.html')

                container.addErrorPages(error401Page, error404Page)
            }
        }
    }

    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter()
        characterEncodingFilter.setEncoding('UTF-8')
        characterEncodingFilter.setForceEncoding(true)

        characterEncodingFilter
    }

    @Bean
    OncePerRequestFilter staticResourcesFilter() {
        new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {
                if (request.getRequestURI().endsWith('.html')) {
                    if (request.getHeader('X-Requested-With') != null
                            && request.getHeader('X-Requested-With')
                            .equalsIgnoreCase('XMLHttpRequest')) {
                        filterChain.doFilter(request, response)
                    }

                    return
                }

                filterChain.doFilter(request, response)
            }
        }
    }
}
