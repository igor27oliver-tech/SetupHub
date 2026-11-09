package DTO;
public record LoginResponse(String token, long expiraEmSegundos, String login) {}