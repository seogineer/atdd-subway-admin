package nextstep.subway.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SectionRequest {
    private Long preStationId;
    private Long stationId;
    private Integer distance;

    @JsonCreator
    public SectionRequest(
            @JsonProperty("preStationId") Long preStationId, @JsonProperty("stationId") Long stationId,
            @JsonProperty("distance") Integer distance) {
        if (distance == null || distance < 0) {
            throw new IllegalArgumentException("허용되지 않는 distance 입니다.");
        }
        if (stationId == null) {
            throw new IllegalArgumentException("허용되지 않는 stationId 입니다.");
        }

        this.preStationId = preStationId;
        this.stationId = stationId;
        this.distance = distance;
    }

    public boolean hasPreStation() {
        return this.preStationId != null;
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }

    public Integer getDistance() {
        return distance;
    }
}
