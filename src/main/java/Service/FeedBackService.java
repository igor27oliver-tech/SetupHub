package Service;

import DTO.FeedBaackResponse;
import DTO.FeedBackRequest;
import Entity.FeedBack;
import Entity.Filme;
import Mapper.FeedBackMapper;
import Repository.FeedBackRepository;
import Repository.RegistersDeFilmes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedBackService {


    private final FeedBackMapper mapper;
    private final RegistersDeFilmes registersDeFilmes;
    private final FeedBackRepository feedBackRepository;

    @Transactional
    public FeedBaackResponse addFeedBack(FeedBackRequest request) {
        Filme filme = registersDeFilmes.findById(request.getIdFilme())
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));

        FeedBack feedBack = mapper.toEntity(request);
        feedBack.setFilme(filme);

        FeedBack salvo = feedBackRepository.save(feedBack);
        return mapper.toResponse(salvo);
    }
}