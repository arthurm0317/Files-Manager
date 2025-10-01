package com.arthur.filesorgs.entities;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

/*
Arquivo{
    name
    base64
    mimetype
    created_at

}

O que fazer com o arquivo?
    -Categorizar
    -Salvar o base64 para download futuro caso necessario
    -inserir em etapas estilo kanban para poder visualizar e organizar
    -editar caso seja planilha ou docx (mammoth para docx e sheetjs para xlsx) -ambos possuem limitações
 */

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String file_name;
    private String Category;
    private String base64;
    private String mimetype;
    private Long created_at;
    private Long updated_at;

    public File() {
    }

    public File( String file_name, String category, String base64, String mimetype, Long created_at, Long updated_at) {
        this.file_name = file_name;
        Category = category;
        this.base64 = base64;
        this.mimetype = mimetype;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Long updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "File{" +
                "file_name='" + file_name + '\'' +
                ", Category='" + Category + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", mimetype='" + mimetype + '\'' +
                ", base64='" + base64 + '\'' +
                '}';
    }
}
