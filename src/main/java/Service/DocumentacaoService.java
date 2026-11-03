package Service;

import DTO.DocumentacaoRequest;
import DTO.DocumentacaoResponse;
import Entity.Filme;
import Entity.FilmeDocumentacao;
import Repository.FilmeDocumentacaoRepository;
import Repository.RegistersDeFilmes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DocumentacaoService {

    @Autowired
    private FilmeDocumentacaoRepository docRepository;
    @Autowired private RegistersDeFilmes registersDeFilmes;

    @Transactional (readOnly = true)
    public DocumentacaoResponse buscar(Long idFilme) {
        return docRepository.findByFilmeId(idFilme)
                .map(this::toResponse)
                .orElse(new DocumentacaoResponse(idFilme, "", null, null, null));
    }

    @Transactional
    public DocumentacaoResponse salvar(DocumentacaoRequest req) {
        Filme filme = registersDeFilmes.findById(req.idFilme())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado"));

        String editor = req.nomeEditor() == null ? "" : req.nomeEditor().trim();
        if (editor.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o nome de quem edita");
        String conteudo = req.conteudo() == null ? "" : req.conteudo();
        if (conteudo.length() > 20000)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Documentação acima de 20000 caracteres");

        FilmeDocumentacao doc = docRepository.findByFilmeId(req.idFilme()).orElse(null);
        if (doc == null) {
            doc = new FilmeDocumentacao();
            doc.setFilme(filme);
        } else if (!Objects.equals(req.versao(), doc.getVersao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Documentação alterada por outra pessoa");
        }
        doc.setConteudo(conteudo);
        doc.setAtualizadoPor(editor);
        doc.setAtualizadoEm(LocalDateTime.now());
        return toResponse(docRepository.saveAndFlush(doc));
    }

    private DocumentacaoResponse toResponse(FilmeDocumentacao d) {
        return new DocumentacaoResponse(d.getFilme().getId(), d.getConteudo(),
                d.getAtualizadoPor(), d.getAtualizadoEm(), d.getVersao());
    }
}