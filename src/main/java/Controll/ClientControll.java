package Controll;

import DTO.*;
import Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;          // <- era jakarta.annotation.Resource
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class ClientControll {
    @Autowired
    RegisterNewFilme registerNewFilme;
    @Autowired
    VisualizationFilmeService visualizationFilmeService;
    @Autowired
    FeedBackService  feedBackService;
    @Autowired
    AdminService adminService;
    @Autowired
    TagsService tagsService;

    @GetMapping ("/listAll")
    public List<FilmeResponse> listAll(){
       return visualizationFilmeService.getAllFilmes();

    }

    @PostMapping ("/addNewSetup")
    public FilmeResponse novoRegistro (@RequestBody RegisterNewSetup newSetup){
       return registerNewFilme.registerNewSetup(newSetup);
    }


    @PostMapping ("/novoFilme")
    public FilmeResponse novoRegistro (@RequestBody RegisterNewFilmeDTO filmeDTO){
      return registerNewFilme.registerNewFilme(filmeDTO);
    }

    @GetMapping ("/ViewForName")
    public List<ViewForName> listaPorNome() {
        return visualizationFilmeService.getAllViewForName();
   }

    @GetMapping ("/filterForID")
    public FilmeResponse seeForID (@RequestParam Long idFilme){
       return visualizationFilmeService.getUnit(idFilme);
    }

    @PostMapping ("/feedBack")
    public FeedBaackResponse feedBack (@Valid @RequestBody FeedBackRequest feedBackRequest){
      return   feedBackService.addFeedBack(feedBackRequest);

    }

    @DeleteMapping ("/deletFilme")
    public FilmeResponse deleteFilme (@RequestParam Long idFilme){
       return adminService.deletFilme(idFilme);
    }

    @GetMapping ("/filterForTag")
    public List<FilmeResponse> seeForTag (TagRequest tagRequest){
     return  visualizationFilmeService.findByTag(tagRequest);
    }

    @PostMapping ("/createNewTag")
    public TagResponse createNewTag (@RequestBody TagRequest tagRequest){
       return tagsService.newTag(tagRequest);
    }
    // adiciona tag a filme ja existe
    @PostMapping ("/addTeg")
    public TagResponse addTeg (@RequestParam Long idFIlme , TagRequest tagRequest){
       return tagsService.addTag(idFIlme, tagRequest);
    }

    @GetMapping ("/allTags")
    public List<TagResponse> allTags (){
      return tagsService.listTags();

    }


    @Autowired ImagemService imagemService;

    @PostMapping(value = "/addImagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImagemResponse addImagem(@RequestParam Long idFilme,
                                    @RequestParam(required = false) Long idSetup,
                                    @RequestParam String nomeOperador,
                                    @RequestParam(required = false) String descricao,
                                    @RequestParam("file") MultipartFile file) throws IOException {
        return imagemService.salvar(idFilme,idSetup, nomeOperador, descricao, file);
    }

    @GetMapping("/imagem/{id}")
    public ResponseEntity<Resource> imagem(@PathVariable Long id) {
        return imagemService.buscar(id);
    }

    @DeleteMapping("/deletImagem")
    public void deletImagem(@RequestParam Long idImagem) throws IOException {
        imagemService.excluir(idImagem);
    }


    @Autowired DocumentacaoService documentacaoService;

    @GetMapping("/documentacao")
    public DocumentacaoResponse documentacao(@RequestParam Long idFilme) {
        return documentacaoService.buscar(idFilme);
    }

    @PutMapping("/documentacao")
    public DocumentacaoResponse salvarDocumentacao(@RequestBody DocumentacaoRequest req) {
        return documentacaoService.salvar(req);
    }
}
