package nextstep.subway.line;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineName {
    @Column(name = "name", unique = true)
    private String value;

    protected LineName() {
    }

    public LineName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String name) {
        validateNonNull(name);
        validateEmptyString(name);
    }

    private void validateNonNull(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("노선 이름은 필수값 입니다.");
        }
    }

    private void validateEmptyString(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("노선 이름이 빈 문자열입니다.");
        }
    }

    public String value() {
        return value;
    }
}