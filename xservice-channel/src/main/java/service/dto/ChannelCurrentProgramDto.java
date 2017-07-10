package service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChannelCurrentProgramDto {

    private List<ProgramDto> programs;

    public List<ProgramDto> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramDto> programs) {
        this.programs = programs;
    }

}
