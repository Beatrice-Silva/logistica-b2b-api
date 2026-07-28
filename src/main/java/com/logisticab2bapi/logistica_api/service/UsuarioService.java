package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.model.Usuario.PerfilRole;
import com.logisticab2bapi.logistica_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;
   
    @Autowired
    private PasswordEncoder encoder; //BCrypt do SecurityConfig
    
    public Usuario register(Usuario user){
        if(user.getNome() == null || user.getNome().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome não preenchido");
        }
        if(user.getEmail() == null || user.getEmail().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não preenchido");
        }
        if(user.getSenha() == null || user.getSenha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não preenchida");
        }
        if(user.getPerfilRole() == null){
            user.setPerfilRole(PerfilRole.OPERADOR); 
        }
        
     
        if(repository.findByEmail(user.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        
        //Criptografar antes de salvar
        user.setSenha(encoder.encode(user.getSenha()));
        return repository.save(user);
    }
    
    
    public Usuario validarLogin(String email, String senha){
        if(email == null || email.isBlank() || senha == null || senha.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha obrigatórios");
        }
        
       
        Usuario user = repository.findByEmail(email).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.UNAUTHORIZED), "Campo email ou senha estão incorretos");
        //comparacao de senha
        if(!encoder.matches(senha, user.getSenha())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Campo email ou senha estão incorretos");
        }
        return user;
    }      
    }