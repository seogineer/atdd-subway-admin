package nextstep.subway.ui;


import java.net.URI;
import java.util.List;
import nextstep.subway.application.LineService;
import nextstep.subway.dto.LineRequestDTO;
import nextstep.subway.dto.LineResponseDTO;
import nextstep.subway.dto.LineResponsesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineController {

    private static final String LINES_PATH = "/lines/";

    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponseDTO> createLines(@RequestBody LineRequestDTO lineRequestDTO) {
        LineResponseDTO lineResponseDTO = lineService.saveLine(lineRequestDTO);
        return ResponseEntity.created(URI.create(LINES_PATH + lineResponseDTO.getId())).body(lineResponseDTO);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponseDTO>> showLines() {
        LineResponsesDTO lineResponsesDTO = lineService.findAll();
        return ResponseEntity.ok(lineResponsesDTO.getLineResponses());
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineResponseDTO> showLine(@PathVariable Long id) {
        LineResponseDTO lineResponsesDTO = lineService.findOne(id);
        return ResponseEntity.ok(lineResponsesDTO);
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity<Void> updateLine(@PathVariable Long id, @RequestBody LineRequestDTO lineRequestDTO) {
        lineService.updateLineInfo(id, lineRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        lineService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }

}