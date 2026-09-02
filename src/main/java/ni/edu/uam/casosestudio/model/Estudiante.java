package ni.edu.uam.casosestudio.model;

import java.util.Date;
import java.util.Objects;

public class Estudiante {
    private String nombre;
    private String apellido;
    private Date fechaDeNacimiento;
    private String departamento;
    private String curso;
    private String modalidad;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, String departamento, Date fechaDeNacimiento, String curso, String modalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.curso = curso;
        this.modalidad = modalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCompleto() {
        if (apellido != null && !apellido.trim().isEmpty()) {
            return (nombre != null ? nombre : "") + " " + apellido;
        }
        return nombre != null ? nombre : "";
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        return Objects.equals(getNombre(), that.getNombre()) && Objects.equals(getApellido(), that.getApellido()) && Objects.equals(getFechaDeNacimiento(), that.getFechaDeNacimiento()) && Objects.equals(getDepartamento(), that.getDepartamento()) && Objects.equals(getCurso(), that.getCurso()) && Objects.equals(getModalidad(), that.getModalidad());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getFechaDeNacimiento(), getDepartamento(), getCurso(), getModalidad());
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", departamento='" + departamento + '\'' +
                ", curso='" + curso + '\'' +
                ", modalidad='" + modalidad + '\'' +
                '}';
    }
}