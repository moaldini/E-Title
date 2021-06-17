package miu.edu.etitle.security;

import miu.edu.etitle.config.JPAUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static String getSSN() {
        JPAUserDetails userDetails = (JPAUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getSsn();
    }

    public static JPAUserDetails getUserDetail() {
        return (JPAUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isOrganizationUser() {
        JPAUserDetails userDetails = (JPAUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getRoles().get(0).getName().equals("ORGANIZATION");
    }
}
