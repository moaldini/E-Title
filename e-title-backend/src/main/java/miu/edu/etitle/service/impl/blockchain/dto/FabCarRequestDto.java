package miu.edu.etitle.service.impl.blockchain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.CreateCarDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class FabCarRequestDto {
    private String fcn;
    private String chaincodeName;
    private String channelName;
    List<String> args;
    List<String> peers;

    public FabCarRequestDto(CreateCarDto dto) {
        this.fcn = dto.getFcn();
        this.chaincodeName = dto.getChaincodeName();
        this.channelName = dto.getChannelName();
        this.peers = dto.getPeers();
        this.args = dto.getArgs();
    }

    public FabCarRequestDto(AddCarDto dto) {
        this.fcn = "createCar";
        this.chaincodeName = "fabcar";
        this.channelName = "mychannel";
        this.peers = new ArrayList<>();
        peers.add("peer0.org1.example.com");
        peers.add("peer0.org2.example.com");
        this.args = new ArrayList<>();
        args.add(dto.getVin());
        args.add(dto.getMake());
        args.add(dto.getModel());
        args.add(dto.getColor());
        args.add(dto.getOwner());
        args.add(dto.getMiles());
        args.add(dto.getPrice());
        args.add(dto.getYear());
        args.add(dto.getCondition());
        args.add(dto.getCylinders());
        args.add(dto.getTransmission());
        args.add(dto.getSize());
        args.add(dto.getFuel());
        args.add(dto.getTitle());
        args.add(dto.getVehicle());
        args.add(dto.getType());
        args.add(dto.getState());
    }
}
