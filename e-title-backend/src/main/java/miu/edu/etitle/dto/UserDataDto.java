package miu.edu.etitle.dto;

import lombok.Getter;
import lombok.Setter;
import miu.edu.etitle.config.JPAUserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserDataDto {
    private String fullName;
    private String email;
    private String avartar;
    private String role;
    private ArrayList<Map<String,String>> ability;

    public UserDataDto(JPAUserDetails userDetails) {
        this.fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        this.email = userDetails.getEmail();
        this.role = userDetails.getRoles().get(0).getName();

        ability = new ArrayList<>();
        Map<String,String> a = new HashMap<>();
        if(this.role.equals("USER")) {
            a.put("action", "read");
            a.put("subject", "ACL");
        } else if(this.role.equals("ORGANIZATION")) {
            a.put("action", "manage");
            a.put("subject", "all");
        }
        ability.add(a);
    }
}