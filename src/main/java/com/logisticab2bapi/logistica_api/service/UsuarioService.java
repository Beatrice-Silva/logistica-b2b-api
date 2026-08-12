package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    
    public void registrar(Usuario user) {
        if(user.getEmail() == null || user.getEmail().equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Email não preenchido");
        }
        if(user.getSenha() == null || user.getSenha().equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Senha não preenchida");
        }
        if(user.getPerfilRole() == null) {
         
            user.setPerfilRole(Usuario.PerfilRole.OPERADOR);
            
        }
        repository.save(user);
    }
    
    public String login(String email, String senha) {
        if(email == null || email.equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Email não preenchido");
        }
        if(senha == null || senha.equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Senha não preenchida");
        }

        Usuario usuario = repository.findByEmail(email).orElse(null);
        
        if(usuario == null || !usuario.getSenha().equals(senha)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Email ou senha inválidos");
        }
        return tokenService.gerarToken(usuario);
    }
    
    public List<Usuario> listarAdmins() {

        return repository.findByPerfilRole(Usuario.PerfilRole.ADMIN);
    }
    
    public List<Usuario> listarOperadores() {
        return repository.findByPerfilRole(Usuario.PerfilRole.OPERADOR);
    }
    
    public List<Usuario> listarEntregadores() {
        return repository.findByPerfilRole(Usuario.PerfilRole.ENTREGADOR);
    }
     
    public List<Usuario> listarTodos() {
       
        return repository.findAll();
    }
        
}