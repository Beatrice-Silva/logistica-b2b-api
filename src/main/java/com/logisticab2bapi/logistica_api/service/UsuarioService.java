package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    //
   public Usuario registrar(Usuario user) {
        
        if(user.getNome() == null || user.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do usuário não foi preenchido!");
        }
        
        if(!user.getNome().trim().matches("^[A-Za-zÀ-ÿ]+\\s+[A-Za-zÀ-ÿ]+$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Digite Nome e Sobrenome");
        }
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não preenchido!");
        }
        if(user.getSenha() == null || user.getSenha().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não preenchida!");
        }
        
        if(!user.getSenha().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha fraca: min 8 caracteres, 1 maiuscula, 1 minuscula, 1 numero e 1 especial");
        }
        if(repository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado!");
        }

        if(user.getPerfilRole() == null) {
            user.setPerfilRole(Usuario.PerfilRole.OPERADOR);
        }
        
        user.setStatusConta(Usuario.StatusConta.PENDENTE); // todo cadastro WEB entra em cooldown
       

        return repository.save(user);
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