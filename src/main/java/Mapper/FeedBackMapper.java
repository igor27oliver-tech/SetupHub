package Mapper;

import DTO.FeedBackRequest;
import DTO.FeedBaackResponse;
import Entity.FeedBack;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class FeedBackMapper {   // sem repository: o Filme é buscado no FeedBackService

    public FeedBack toEntity(FeedBackRequest request) {
        FeedBack feedBack = new FeedBack();
        feedBack.setNomeUsuario(request.getNomeUsuario());
        feedBack.setComentario(request.getComentario());
        feedBack.setData(LocalDateTime.now());
        return feedBack;
    }

    public FeedBaackResponse toResponse(FeedBack feedBack) {
        FeedBaackResponse response = new FeedBaackResponse();
        response.setId(feedBack.getId());
        response.setNomeUsuario(feedBack.getNomeUsuario());
        response.setComentario(feedBack.getComentario());
        response.setData(feedBack.getData());   // sem sobrescrever com now()
        return response;
    }

    public List<FeedBaackResponse> toResponseList(List<FeedBack> feedBacks) {
        if (feedBacks == null) {
            return Collections.emptyList();
        }
        return feedBacks.stream().map(this::toResponse).toList();
    }
}