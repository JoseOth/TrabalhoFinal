package com.example.trabalhofinal;

import java.io.Serializable;

public class Permissao implements Serializable {
    private int id, idArtigo;
    private int idUsuario1, idUsuario2, idUsuario3, idUsuario4, idUsuario5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdArtigo() {
        return idArtigo;
    }

    public void setIdArtigo(int idArtigo) {
        this.idArtigo = idArtigo;
    }

    public int getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(int idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    public int getIdUsuario3() {
        return idUsuario3;
    }

    public void setIdUsuario3(int idUsuario3) {
        this.idUsuario3 = idUsuario3;
    }

    public int getIdUsuario4() {
        return idUsuario4;
    }

    public void setIdUsuario4(int idUsuario4) {
        this.idUsuario4 = idUsuario4;
    }

    public int getIdUsuario5() {
        return idUsuario5;
    }

    public void setIdUsuario5(int idUsuario5) {
        this.idUsuario5 = idUsuario5;
    }
}
