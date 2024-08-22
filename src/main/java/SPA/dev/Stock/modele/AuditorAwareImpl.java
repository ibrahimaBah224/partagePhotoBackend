package SPA.dev.Stock.modele;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        // Assuming your UserDetails class returns the user ID
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer userId = ((User) userDetails).getId(); // Adjust this line based on your UserDetails implementation

        return Optional.of(userId);
    }
}

