package io.github.jonasnascc.SeuSindico_API.security.utils;

import io.github.jonasnascc.SeuSindico_API.dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public abstract class SecurityUtils {
    @Autowired
    public UsuarioRepository usuarioRepository;

    public String obterLoginAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails userDetails)
        {
            return userDetails.getUsername();
        } else {
            return "";
        }
    }
}
