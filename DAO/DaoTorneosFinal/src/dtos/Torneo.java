package dtos;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

import java.time.LocalDate;

public class Torneo {

    private int idTorneo;
    private String nombre;
    private String disciplina;
    private int noEquipos;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int noGrupos;
    private double porcentajeAvance;

    public Torneo() {
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getNoEquipos() {
        return noEquipos;
    }

    public void setNoEquipos(int noEquipos) {
        this.noEquipos = noEquipos;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNoGrupos() {
        return noGrupos;
    }

    public void setNoGrupos(int noGrupos) {
        this.noGrupos = noGrupos;
    }

    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }


    @Override
    public String toString() {
        return "Torneo{" +
                "idTorneo=" + idTorneo +
                ", nombre='" + nombre + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", noEquipos=" + noEquipos +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", noGrupos=" + noGrupos +
                ", porcentajeAvance=" + porcentajeAvance +
                '}';

    }

}
