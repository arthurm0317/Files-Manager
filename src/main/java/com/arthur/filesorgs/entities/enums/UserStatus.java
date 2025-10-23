package com.arthur.filesorgs.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    //Status para confirmar email com url/codigo (ainda nao sei ao certo qual será)
    ATIVO("A", "Ativo"),
    INATIVO("I", "Inativo"),
    PENDENTE("P", "Pendente");

    //Sem isso não conseguimos colocar os parametros no enum
    private String codigo;
    private String descricao;

    private UserStatus(String codigo, String descricao){
        this.codigo = codigo;
        this.descricao=descricao;
    }
    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //Retornar o enum de acordo com o codigo
    @JsonCreator
    public static UserStatus status(String codigo){
        return switch (codigo) {
            case "A" -> ATIVO;
            case "I" -> INATIVO;
            case "P" -> PENDENTE;
            default -> PENDENTE;
        };
    }

}
