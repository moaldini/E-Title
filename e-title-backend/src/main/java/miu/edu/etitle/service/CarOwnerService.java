package miu.edu.etitle.service;

import miu.edu.etitle.domain.CarOwner;

public interface CarOwnerService {
    CarOwner findBySSN(String ssn);
}
