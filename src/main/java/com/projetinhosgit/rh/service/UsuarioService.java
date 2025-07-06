package com.projetinhosgit.rh.service;

// Importações necessárias
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetinhosgit.rh.model.Usuario;
import com.projetinhosgit.rh.repository.FuncionarioRepository;
import com.projetinhosgit.rh.repository.UsuarioRepository;

// Anotação que indica que essa classe é um "Service" do Spring, ou seja,
// uma camada intermediária entre o Controller (entrada) e o Repository (acesso ao banco)
@Service
public class UsuarioService {

	 
    // Injeção do repositório de usuário para acessar dados no banco
    private final UsuarioRepository usuarioRepository;

    // Injeção do codificador BCrypt (definido em SecurityConfig) para comparar senhas
    private final BCryptPasswordEncoder passwordEncoder;

    // Construtor com injeção de dependências
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Atualizar senha
    public String atualizarSenha(String login, String senhaAnterior,String novaSenha) {
    	Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findByLogin(login);
    	
    	//se o usuário existir, compara com a senha anterior(atual)
    	if (usuarioExistenteOptional.isPresent()) {
			Usuario usuarioExistente = usuarioExistenteOptional.get();
			boolean autenticado = autenticar(login, senhaAnterior);
			if (autenticado) {
				String senhaCodificada	=passwordEncoder.encode(novaSenha);
				usuarioExistente.setSenhaHash(senhaCodificada);
				usuarioRepository.save(usuarioExistente);
				return "Senha de " +usuarioExistente.getLogin() +" atualizada com sucesso.";
			
			}
		}
    	return "Senha inválida";
    }
    //método salvarUsuario
    public Usuario salvarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar
    	if (!loginJaExiste(usuario.getLogin())) {
    		   String senhaCriptografada = passwordEncoder.encode(usuario.getSenhaHash());
    	        usuario.setSenhaHash(senhaCriptografada);
    	        return usuarioRepository.save(usuario);
		}
    	return null;
    }

    //verifca se existe um login existente
    public boolean loginJaExiste(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

   
    /**
     * Método que verifica se o login e senha informados são válidos
     *
     * @param login - login informado pelo usuário
     * @param senhaDigitada - senha digitada (em texto plano)
     * @return true se o login existir e a senha for compatível com o hash no banco
     */
    public boolean autenticar(String login, String senhaDigitada) {
        // Busca o usuário pelo login no banco de dados
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

        // Se o usuário existir, compara a senha digitada com a senha hash usando BCrypt
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara a senha digitada com a senha criptografada salva no banco
            return passwordEncoder.matches(senhaDigitada, usuario.getSenhaHash());
        }

        // Se o login não existir, retorna false
        return false;
    }
    
    
}
