package ni.edu.uam.casosestudio.service;

import ni.edu.uam.casosestudio.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteService {
    private final List<Estudiante> estudiantes;

    public EstudianteService() {
        this.estudiantes = new ArrayList<>();
    }

    public boolean addEstudiante(Estudiante estudiante){
        return estudiantes.add(estudiante);
    }

    public List<Estudiante> getAllEstudiantes(){
        return estudiantes;
    }

    public boolean removeEstudiante(Estudiante estudiante){
        return estudiantes.remove(estudiante);
    }

    public boolean updateEstudiante(int index, Estudiante estudiante){
        if (index >= 0 && index < estudiantes.size()) {
            estudiantes.set(index, estudiante);
            return true;
        }
        return false;
    }
}
