package com.github.lavenderx.config

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.ErrorPage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by lavenderx on 2016-04-30.
 */
@Configuration
class BeanInitializer {

    @Order(1)
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        new EmbeddedServletContainerCustomizer() {
            @Override
            void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, '/views/401.html')
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, '/views/404.html')

                container.addErrorPages(error401Page, error404Page)
            }
        }
    }

    @Order(1)
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter()
        characterEncodingFilter.setEncoding('UTF-8')
        characterEncodingFilter.setForceEncoding(true)

        characterEncodingFilter
    }

    @Order(1)
    @Bean
    public OncePerRequestFilter staticResourcesFilter() {
        new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {
                if (request.getRequestURI().endsWith('.html')) {
                    if (request.getHeader('X-Requested-With') != null
                            && request.getHeader('X-Requested-With').equalsIgnoreCase('XMLHttpRequest')) {
                        filterChain.doFilter(request, response)
                    }
                    return
                }

                filterChain.doFilter(request, response)
            }
        }
    }
}
