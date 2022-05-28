package nextstep.subway.dto;

import nextstep.subway.domain.Station;

public class StationRequestDTO {
    private String name;

    public String getName() {
        return name;
    }

    public Station toStation() {
        return new Station(name);
    }
}