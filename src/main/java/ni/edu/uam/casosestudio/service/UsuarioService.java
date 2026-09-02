package ni.edu.uam.casosestudio.service;

import ni.edu.uam.casosestudio.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios;

    public UsuarioService(){
        this.usuarios = new ArrayList<>();
        Usuario deffaultUser = new Usuario("admin", "admin");
        usuarios.add(deffaultUser);
    }

    public List<Usuario> getAllUsuarios(){
        return usuarios;
    }
}
