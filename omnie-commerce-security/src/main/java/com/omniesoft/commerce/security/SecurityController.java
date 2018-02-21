package com.omniesoft.commerce.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitalii Martynovskyi
 * @since 19.09.17
 */
@RestController
public class SecurityController {

    @GetMapping(path = "/status")
    public void statusForDocker() {
    }
}
