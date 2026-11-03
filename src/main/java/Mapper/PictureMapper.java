//package Mapper;
//
//import DTO.PictureRequest;
//import DTO.PictureResponse;
//import Entity.Picture;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PictureMapper {
//
//
//
//  public List<PictureResponse> toResponse (List<Picture> picture) {
//      List<PictureResponse> pictureResponseList = new ArrayList<>();
//
//     for (Picture picture1 : picture) {
//         PictureResponse pictureResponse = new PictureResponse();
//         pictureResponse.setLocalDateTime(LocalDateTime.now());
//         pictureResponse.setPicture(picture1.getPicture());
//         pictureResponseList.add(pictureResponse);
//     }
//
//      return pictureResponseList;
//
//    }
//
//  public Picture toEntit (PictureRequest request) {
//      Picture picture = new Picture();
//      picture.setPicture(request.picture());
//      picture.setDateTime(LocalDateTime.now());
//      return picture;
//
//  }
//
//
//
//
//}
