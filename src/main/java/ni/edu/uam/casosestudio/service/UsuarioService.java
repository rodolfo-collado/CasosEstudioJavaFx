package ni.edu.uam.casosestudio.service;

import ni.edu.uam.casosestudio.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios;

    public UsuarioService(){
        this.usuarios = new ArrayList<>();
        Usuario defaultUser = new Usuario("admin", "admin");
        Usuario defaultAdmin8 = new Usuario("admin", "admin1234");
        usuarios.add(defaultUser);
        usuarios.add(defaultAdmin8);
    }

    public List<Usuario> getAllUsuarios(){
        return usuarios;
    }

    public boolean esAdmin(String username, String password) {
        return usuarios.stream()
                .anyMatch(usuario -> "admin".equals(usuario.getUsername())
                        && usuario.getUsername().equals(username)
                        && usuario.getPassword().equals(password));
    }
}
