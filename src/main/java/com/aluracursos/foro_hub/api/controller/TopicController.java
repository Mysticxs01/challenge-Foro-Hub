package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.model.*;
import com.aluracursos.foro_hub.api.model.dto.topico.ActulizaTopico;
import com.aluracursos.foro_hub.api.model.dto.topico.DatosListadoTopico;
import com.aluracursos.foro_hub.api.model.dto.topico.DatosRegistroTopico;
import com.aluracursos.foro_hub.api.model.exception.ValidarNoDuplicadoTopicoException;
import com.aluracursos.foro_hub.api.repository.TopicoRepository;
import com.aluracursos.foro_hub.api.service.TopicoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/topicos")
public class TopicController {

    private TopicoRepository topicoRepositorio;
    private TopicoService topicoService;

    public TopicController(TopicoRepository topicoRepositorio, TopicoService topicoService) {
        this.topicoRepositorio = topicoRepositorio;
        this.topicoService = topicoService;
    }
    @PostMapping
    public ResponseEntity<String> registrarTopic(@RequestBody DatosRegistroTopico datosTopico){
        try {
            topicoRepositorio.save(new Topico(datosTopico));
        } catch (Exception e) {
            return ResponseEntity.ok("Ese post ya existe, sin cambios en la BD");
        }
       return null;


    }
    @GetMapping
    public Page<DatosListadoTopico> listadoTopicos(Pageable paginacion){
        return topicoRepositorio.findAll(paginacion).map(DatosListadoTopico::new);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Topico> BuqueraPorID(@PathVariable Long id){
            Topico topico = topicoService.buscarTopicoByID(id);
            if(topico != null){
                return ResponseEntity.ok(topico);
            }else{
                return ResponseEntity.notFound().build();
            }
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> actualizarTopico(@PathVariable Long id, @RequestBody ActulizaTopico actualizaTopico)  {
        try {
            Topico topico = topicoService.buscarTopicoByID(id);
            topicoService.actualizarDatos(actualizaTopico, topico);
        } catch (ValidarNoDuplicadoTopicoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return null;
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id){
      Optional<Topico> datoEnBase = Optional.ofNullable(topicoService.buscarTopicoByID(id));
      if (datoEnBase.isPresent()){
          topicoRepositorio.deleteById(id);
          return ResponseEntity.ok("Elemento borrado");
      }else {
          return ResponseEntity.ok("Ningun Elemento con ese ID");
      }
    }



}
