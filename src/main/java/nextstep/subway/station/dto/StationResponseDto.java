package nextstep.subway.station.dto;

import java.time.LocalDateTime;
import nextstep.subway.station.Station;

public class StationResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private StationResponseDto(Long id, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static StationResponseDto of(Station station) {
        return new StationResponseDto(station.getId(), station.getName(), station.getCreatedDate(), station.getModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}