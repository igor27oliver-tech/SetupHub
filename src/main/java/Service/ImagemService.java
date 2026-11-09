package Service;



import DTO.ImagemResponse;
import Entity.Filme;
import Entity.FilmeImagem;
import Entity.SetupUnit;
import Repository.FilmeImagemRepository;
import Repository.RegistersDeFilmes;

import Repository.SetupUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class ImagemService {

    private static final Map<String, String> EXTENSOES = Map.of(
            "image/jpeg", ".jpg", "image/png", ".png", "image/webp", ".webp");

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Autowired
    private FilmeImagemRepository imagemRepository;
    @Autowired private RegistersDeFilmes registersDeFilmes;
    @Autowired
    SetupUnitRepository setupRepository;


    @Transactional
    public ImagemResponse salvar(Long idFilme, Long idSetup,String nomeOperador, String descricao, MultipartFile file) throws IOException {
        Filme filme = registersDeFilmes.findById(idFilme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado: " + idFilme));

        if (file.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo vazio");
        String ext = EXTENSOES.get(file.getContentType());
        if (ext == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Use JPG, PNG ou WEBP");

        SetupUnit setup = null;
        if (idSetup != null) {
            setup = setupRepository.findById(idSetup)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setup não encontrado"));
            if (!setup.getFilme().getId().equals(idFilme))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O setup não pertence a este filme");
        }

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dir);
        String nomeArquivo = UUID.randomUUID() + ext;      // nunca usa o nome enviado pelo usuário
        Files.copy(file.getInputStream(), dir.resolve(nomeArquivo));

        FilmeImagem img = new FilmeImagem();

        img.setFilme(filme);
        img.setSetup(setup);
        img.setNomeArquivo(nomeArquivo);
        img.setNomeOriginal(file.getOriginalFilename());
        img.setContentType(file.getContentType());
        img.setNomeOperador(nomeOperador);
        img.setDescricao(descricao);
        img.setDataUpload(LocalDateTime.now());
        return toResponse(imagemRepository.save(img));
    }

    public ResponseEntity<Resource> buscar(Long id) {
        FilmeImagem img = imagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Path arquivo = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(img.getNomeArquivo());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getContentType()))
                .cacheControl(CacheControl.maxAge(Duration.ofDays(7)))
                .body(new FileSystemResource(arquivo));
    }

    @Transactional
    public void excluir(Long id) throws IOException {
        FilmeImagem img = imagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Files.deleteIfExists(Paths.get(uploadDir).toAbsolutePath().normalize().resolve(img.getNomeArquivo()));
        imagemRepository.delete(img);
    }

    private ImagemResponse toResponse(FilmeImagem i) {
        return new ImagemResponse(i.getId(), "/imagem/" + i.getId(), i.getNomeOperador(), i.getDescricao(), i.getDataUpload(), i.getSetup() == null ? null : i.getSetup().getId());
    }
}