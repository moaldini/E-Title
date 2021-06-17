package miu.edu.etitle.service.impl.blockchain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FabCarResponseDto {
    Result ResultObject;
    private String error = null;
    private String errorData = null;
}

@Getter
@Setter
@ToString
class Result {
    private String message;
    private Response result;
}

@Getter
@Setter
@ToString
class Response {
    private String make;
    private String model;
    private String colour;
    private String owner;
    private String odometer;
    private String price;
    private String year;
    private String condition;
    private String cylinders;
    private String transmission;
    private String size;
    private String fuel;
    private String title_status;
    private String fwd;
    private String body_type;
    private String state;
}