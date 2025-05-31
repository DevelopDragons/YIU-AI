package devdragons.yiuServer.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class CommonController<T, R> {
    protected abstract boolean createEntity(R requestDto) throws Exception;

    protected abstract boolean updateEntity(Integer id, R requestDto) throws Exception;

    protected abstract boolean deleteEntity(Integer id) throws Exception;

    protected abstract List<T> getEntities() throws Exception;

    @PostMapping("/admin")
    public ResponseEntity<Boolean> create(@ModelAttribute R requestDto) throws Exception {
        return new ResponseEntity<>(createEntity(requestDto), HttpStatus.OK);
    }

    @PutMapping("/admin")
    public ResponseEntity<Boolean> update(@RequestParam("id") Integer id, @ModelAttribute R requestDto) throws Exception {
        return new ResponseEntity<>(updateEntity(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> delete(@RequestParam(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(deleteEntity(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<T>> getAll() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(getEntities(), HttpStatus.OK);
    }
}
