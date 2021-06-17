package miu.edu.etitle.dto;

import lombok.*;
import miu.edu.etitle.domain.Car;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddCarDto {
    private String vin;
    private String make;
    private String model;
    private String color;
    private String owner;
    private String miles;
    private String price;
    private String year;
    private String condition;
    private String cylinders;
    private String transmission;
    private String size;
    private String fuel;
    private String title;
    private String vehicle;
    private String type;
    private String state;
    private String status;

    public AddCarDto(Car car) {
        vin = car.getVin();
        make = car.getMake() != null ? car.getMake().getName() : "";
        model = car.getModel() != null ? car.getModel().getName() : "";
        color = car.getColor();
        owner = car.getCarOwner().getSsn();
        miles = String.valueOf(car.getCurrentRevision().getMiles());
        price = String.valueOf(car.getCurrentRevision().getPrice());
        year =  String.valueOf(car.getYear());
        condition = car.getCurrentRevision().getCondition();
        cylinders = String.valueOf(car.getCylinders());
        transmission = car.getTransmission();
        size = car.getSize();
        fuel = car.getModelFuelType() != null ? car.getModelFuelType().name() : "";
        title = car.getCurrentRevision().getStatus();
        vehicle = car.getFwd();
        type = car.getBodyType();
        state = car.getCurrentRevision().getOwner().getAddress().getState().getName();
        status = car.getStatus();
    }
}
