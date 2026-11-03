package DTO;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

public record PictureRequest (List<BufferedImage> picture, LocalDateTime dateTime) {

}
