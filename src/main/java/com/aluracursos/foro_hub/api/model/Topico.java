package com.aluracursos.foro_hub.api.model;

import com.aluracursos.foro_hub.api.model.dto.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name= "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_de_creacion", nullable = true)
    private LocalDateTime fechaCreacion;
    @Column(name = "status_topic", nullable = true)
    private Integer estadoTopico;
    @Column(name = "autor")
    private String autor;
    @Column(name = "curso")
    private String curso;

    public Topico() {
    }

    public Topico(DatosRegistroTopico datosTopico) {
        this.titulo = datosTopico.titulo();
        this.mensaje =datosTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.estadoTopico = 1;
        this.autor = datosTopico.autor();
        this.curso = datosTopico.curso();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getEstadoTopico() {
        return estadoTopico;
    }

    public void setEstadoTopico(Integer estadoTopico) {
        this.estadoTopico = estadoTopico;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


}
