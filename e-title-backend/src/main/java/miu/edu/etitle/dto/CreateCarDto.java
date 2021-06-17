package miu.edu.etitle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CreateCarDto {
    private String fcn;
    private String chaincodeName;
    private String channelName;

    private ArrayList<String> peers;
    private ArrayList<String> args;
}
