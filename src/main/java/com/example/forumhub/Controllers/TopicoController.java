package com.example.forumhub.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumhub.DTO.TopicoDTO;
import com.example.forumhub.Services.TopicoService;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping("/insert")
    public ResponseEntity<TopicoDTO> insert(@RequestBody TopicoDTO dto){
        TopicoDTO finaldto = service.insert(dto);
        return ResponseEntity.ok().body(finaldto);
    }

    @GetMapping
    public Page<TopicoDTO> listarTopicos(
    @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> findById(@PathVariable Long id){
        TopicoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> update(@PathVariable Long id, @RequestBody TopicoDTO dto){
        service.update(id, dto);
        TopicoDTO atual = service.findById(id);
        return ResponseEntity.ok().body(atual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().body("Usuario deletado!");
    }
    
}
