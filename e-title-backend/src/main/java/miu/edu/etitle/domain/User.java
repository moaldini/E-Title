package miu.edu.etitle.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import miu.edu.etitle.domain.address.Address;
import miu.edu.etitle.dto.RegisterUserDto;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"SSN"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    private Organization organization;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private String ssn;
    private String identifyUrl;
    private UserStatus status = UserStatus.REGISTERED;
    private boolean isActive = true;
    private String blockchainToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Address address;

    @OneToOne
    @JoinColumn
    private Role role;

    public User(RegisterUserDto registerUserDto, Address address) {
        this.email = registerUserDto.getEmail();
        this.password = registerUserDto.getPassword();
        this.firstName = registerUserDto.getFirstName();
        this.lastName = registerUserDto.getLastName();
        this.mobile = registerUserDto.getMobile();
        this.ssn = registerUserDto.getSsn();
        this.identifyUrl = registerUserDto.getIdentifyUrl();
        this.address = address;
    }
}